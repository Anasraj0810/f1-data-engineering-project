from pyspark.sql import SparkSession
from pyspark.sql import functions as F

from log import get_logger

logger = get_logger("silver_to_gold")

spark = SparkSession.builder.appName("silver_to_gold").getOrCreate()

HDFS_BASE = "hdfs://ip-172-31-3-251.eu-west-2.compute.internal:8020/tmp/anas_proj2"

try:
    logger.info("Starting silver to gold job")

    drivers_df = spark.read.parquet(HDFS_BASE + "/silver/drivers")
    constructors_df = spark.read.parquet(HDFS_BASE + "/silver/constructors")
    races_df = spark.read.parquet(HDFS_BASE + "/silver/races")
    results_df = spark.read.parquet(HDFS_BASE + "/silver/results")

    logger.info("Drivers silver count: %s", drivers_df.count())
    logger.info("Constructors silver count: %s", constructors_df.count())
    logger.info("Races silver count: %s", races_df.count())
    logger.info("Results silver count: %s", results_df.count())

    drivers_dim = drivers_df.select(
        "driverid",
        F.concat_ws(" ", F.col("forename"), F.col("surname")).alias("driver_name"),
        F.col("nationality").alias("driver_nationality")
    )

    constructors_dim = constructors_df.select(
        "constructorid",
        F.col("name").alias("constructor_name"),
        F.col("nationality").alias("constructor_nationality")
    )

    races_dim = races_df.select(
        "raceid",
        F.col("name").alias("race_name"),
        "year",
        "round",
        F.col("date").alias("race_date")
    )

    results_fact = results_df.select(
        "resultid",
        "raceid",
        "driverid",
        "constructorid",
        "grid",
        "position",
        "positionorder",
        "points",
        "laps",
        "fastestlap",
        "fastestlapspeed"
    )

    race_results_gold = results_fact.alias("res") \
        .join(races_dim.alias("rac"), "raceid", "left") \
        .join(drivers_dim.alias("drv"), "driverid", "left") \
        .join(constructors_dim.alias("con"), "constructorid", "left") \
        .select(
            "resultid",
            "raceid",
            "race_name",
            "year",
            "round",
            "race_date",
            "driverid",
            "driver_name",
            "driver_nationality",
            "constructorid",
            "constructor_name",
            "constructor_nationality",
            "grid",
            "position",
            "positionorder",
            "points",
            "laps",
            "fastestlap",
            "fastestlapspeed"
        )

    logger.info("Gold race_results count: %s", race_results_gold.count())

    logger.info("Writing gold race_results partitioned by year")
    race_results_gold.write \
        .mode("overwrite") \
        .partitionBy("year") \
        .parquet(HDFS_BASE + "/gold/race_results")

    logger.info("Silver to gold job completed successfully")

except Exception as e:
    logger.exception("Silver to gold job failed: %s", e)
    raise

finally:
    spark.stop()