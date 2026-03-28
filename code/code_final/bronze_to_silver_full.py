from pyspark.sql import SparkSession
from pyspark.sql import functions as F

from utils_cleaning import basic_clean
from log import get_logger

logger = get_logger("bronze_to_silver_full")

spark = SparkSession.builder.appName("bronze_to_silver_full").getOrCreate()

try:
    logger.info("Starting full bronze to silver job")

    drivers_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/drivers/full",
        header=False,
        inferSchema=True
    ).toDF(
        "driverid", "driverref", "number", "code",
        "forename", "surname", "dob", "nationality", "url"
    )

    constructors_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/constructors/full",
        header=False,
        inferSchema=True
    ).toDF(
        "constructorid", "constructorref", "name", "nationality", "url"
    )

    races_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/races/full/races_initial.csv",
        header=True,
        inferSchema=True
    )

    results_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/results/full/results_initial.csv",
        header=True,
        inferSchema=True
    )

    logger.info("Drivers bronze count: %s", drivers_df.count())
    logger.info("Constructors bronze count: %s", constructors_df.count())
    logger.info("Races bronze count: %s", races_df.count())
    logger.info("Results bronze count: %s", results_df.count())

    drivers_cleaned_df = basic_clean(drivers_df) \
        .withColumn("driverid", F.col("driverid").cast("int")) \
        .withColumn("number", F.col("number").cast("int")) \
        .withColumn(
            "dob",
            F.when(
                F.col("dob").rlike(r"^\d{2}/\d{2}/\d{4}$"),
                F.to_date(F.col("dob"), "dd/MM/yyyy")
            ).when(
                F.col("dob").rlike(r"^\d{4}-\d{2}-\d{2}$"),
                F.to_date(F.col("dob"), "yyyy-MM-dd")
            ).otherwise(None)
        ) \
        .filter(F.col("driverid").isNotNull())

    constructors_cleaned_df = basic_clean(constructors_df) \
        .withColumn("constructorid", F.col("constructorid").cast("int")) \
        .filter(F.col("constructorid").isNotNull())

    races_cleaned_df = basic_clean(races_df) \
        .withColumn("raceid", F.col("raceid").cast("int")) \
        .withColumn("year", F.col("year").cast("int")) \
        .withColumn("round", F.col("round").cast("int")) \
        .withColumn("circuitid", F.col("circuitid").cast("int")) \
        .withColumn("date", F.to_date(F.col("date"), "yyyy-MM-dd")) \
        .filter(F.col("raceid").isNotNull())

    results_cleaned_df = basic_clean(results_df) \
        .withColumn("resultid", F.col("resultid").cast("int")) \
        .withColumn("raceid", F.col("raceid").cast("int")) \
        .withColumn("driverid", F.col("driverid").cast("int")) \
        .withColumn("constructorid", F.col("constructorid").cast("int")) \
        .withColumn("number", F.col("number").cast("int")) \
        .withColumn("grid", F.col("grid").cast("int")) \
        .withColumn("position", F.col("position").cast("int")) \
        .withColumn("positionorder", F.col("positionorder").cast("int")) \
        .withColumn("points", F.col("points").cast("double")) \
        .withColumn("laps", F.col("laps").cast("int")) \
        .withColumn("milliseconds", F.col("milliseconds").cast("bigint")) \
        .withColumn("fastestlap", F.col("fastestlap").cast("int")) \
        .withColumn("rank", F.col("rank").cast("int")) \
        .withColumn("fastestlapspeed", F.col("fastestlapspeed").cast("double")) \
        .filter(
            F.col("raceid").isNotNull() &
            F.col("driverid").isNotNull() &
            F.col("constructorid").isNotNull()
        )

    logger.info("Drivers silver cleaned count: %s", drivers_cleaned_df.count())
    logger.info("Constructors silver cleaned count: %s", constructors_cleaned_df.count())
    logger.info("Races silver cleaned count: %s", races_cleaned_df.count())
    logger.info("Results silver cleaned count: %s", results_cleaned_df.count())

    results_with_year_df = results_cleaned_df.join(
        races_cleaned_df.select("raceid", "year"),
        on="raceid",
        how="left"
    )

    logger.info("Writing drivers silver")
    drivers_cleaned_df.coalesce(1).write.mode("overwrite").parquet("/tmp/anas_proj2/silver/drivers")

    logger.info("Writing constructors silver")
    constructors_cleaned_df.coalesce(1).write.mode("overwrite").parquet("/tmp/anas_proj2/silver/constructors")

    logger.info("Writing races silver partitioned by year")
    races_cleaned_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/races")

    logger.info("Writing results silver partitioned by year")
    results_with_year_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/results")

    logger.info("Full bronze to silver job completed successfully")

except Exception as e:
    logger.exception("Full bronze to silver job failed: %s", e)
    raise

finally:
    spark.stop()