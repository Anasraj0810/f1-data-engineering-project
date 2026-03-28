from pyspark.sql import SparkSession
from pyspark.sql import functions as F
import subprocess

from utils_cleaning import basic_clean
from log import get_logger

logger = get_logger("incremental_silver_merge")

spark = SparkSession.builder.appName("incremental_silver_merge").getOrCreate()

try:
    logger.info("Starting incremental silver merge job")

    races_inc_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/races/incremental/races_incremental.csv",
        header=True,
        inferSchema=True
    )

    results_inc_df = spark.read.csv(
        "/tmp/anas_proj2/bronze/results/incremental/results_incremental.csv",
        header=True,
        inferSchema=True
    )

    logger.info("Incremental races bronze count: %s", races_inc_df.count())
    logger.info("Incremental results bronze count: %s", results_inc_df.count())

    races_inc_clean = basic_clean(races_inc_df) \
        .withColumn("raceid", F.col("raceid").cast("int")) \
        .withColumn("year", F.col("year").cast("int")) \
        .withColumn("round", F.col("round").cast("int")) \
        .withColumn("circuitid", F.col("circuitid").cast("int")) \
        .withColumn("date", F.to_date(F.col("date"), "yyyy-MM-dd")) \
        .filter(F.col("raceid").isNotNull())

    results_inc_clean = basic_clean(results_inc_df) \
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

    logger.info("Incremental races cleaned count: %s", races_inc_clean.count())
    logger.info("Incremental results cleaned count: %s", results_inc_clean.count())

    races_main_df = spark.read.parquet("/tmp/anas_proj2/silver/races")
    results_main_df = spark.read.parquet("/tmp/anas_proj2/silver/results")

    logger.info("Existing silver races count: %s", races_main_df.count())
    logger.info("Existing silver results count: %s", results_main_df.count())

    results_inc_with_year = results_inc_clean.join(
        races_inc_clean.select("raceid", "year"),
        on="raceid",
        how="left"
    )

    merged_races_df = races_main_df.unionByName(races_inc_clean).dropDuplicates(["raceid"])
    merged_results_df = results_main_df.unionByName(results_inc_with_year).dropDuplicates(["resultid"])

    logger.info("Merged races count: %s", merged_races_df.count())
    logger.info("Merged results count: %s", merged_results_df.count())

    logger.info("Writing merged races to temp silver path")
    merged_races_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/races_temp")

    logger.info("Writing merged results to temp silver path")
    merged_results_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/results_temp")

    logger.info("Spark merge phase completed successfully")

except Exception as e:
    logger.exception("Incremental silver merge failed during Spark phase: %s", e)
    raise

finally:
    spark.stop()

try:
    logger.info("Replacing main silver races folder with temp folder")
    subprocess.run(["hdfs", "dfs", "-rm", "-r", "/tmp/anas_proj2/silver/races"], check=False)
    subprocess.run(["hdfs", "dfs", "-mv", "/tmp/anas_proj2/silver/races_temp", "/tmp/anas_proj2/silver/races"], check=True)

    logger.info("Replacing main silver results folder with temp folder")
    subprocess.run(["hdfs", "dfs", "-rm", "-r", "/tmp/anas_proj2/silver/results"], check=False)
    subprocess.run(["hdfs", "dfs", "-mv", "/tmp/anas_proj2/silver/results_temp", "/tmp/anas_proj2/silver/results"], check=True)

    logger.info("Incremental silver merge completed successfully")

except Exception as e:
    logger.exception("Incremental silver merge failed during HDFS replace phase: %s", e)
    raise