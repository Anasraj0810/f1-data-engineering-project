import logging
from pyspark.sql import SparkSession
from pyspark.sql import functions as F

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [SILVER] %(message)s",
    datefmt="%H:%M:%S"
)

logger = logging.getLogger(__name__)

HDFS_BASE = "hdfs://ip-172-31-3-251.eu-west-2.compute.internal:8020/tmp/anas_proj2"
BRONZE_PATH = HDFS_BASE + "/bronze/live_lap_times"
SILVER_PATH = HDFS_BASE + "/silver/live_lap_times"


def main():
    spark = SparkSession.builder.appName("bronze_to_silver_stream").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    logger.info("Reading Bronze streaming data from: %s", BRONZE_PATH)

    df = spark.read.parquet(BRONZE_PATH)

    logger.info("Bronze row count: %s", df.count())

    df_clean = (
        df.select(
            F.col("raceid").cast("int").alias("raceid"),
            F.col("driverid").cast("int").alias("driverid"),
            F.col("lap").cast("int").alias("lap"),
            F.col("position").cast("int").alias("position"),
            F.trim(F.col("lap_time")).alias("lap_time"),
            F.col("milliseconds").cast("int").alias("milliseconds"),
            F.col("event_ts").alias("event_ts")
        )
        .filter(
            F.col("raceid").isNotNull() &
            F.col("driverid").isNotNull() &
            F.col("lap").isNotNull()
        )
        .filter(F.col("milliseconds").isNotNull())
        .dropDuplicates(["raceid", "driverid", "lap"])
        .withColumn("ingest_date", F.to_date("event_ts"))
    )

    logger.info("Silver cleaned row count: %s", df_clean.count())
    logger.info("Writing Silver streaming data to: %s", SILVER_PATH)

    (
        df_clean.write
        .mode("overwrite")
        .partitionBy("ingest_date")
        .parquet(SILVER_PATH)
    )

    logger.info("Streaming Bronze to Silver completed successfully")

    spark.stop()


if __name__ == "__main__":
    main()