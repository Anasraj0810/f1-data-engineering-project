import logging
from pyspark.sql import SparkSession
from pyspark.sql import functions as F

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [GOLD] %(message)s",
    datefmt="%H:%M:%S"
)

logger = logging.getLogger(__name__)

HDFS_BASE = "hdfs://ip-172-31-3-251.eu-west-2.compute.internal:8020/tmp/anas_proj2"
SILVER_PATH = HDFS_BASE + "/silver/live_lap_times"
GOLD_PATH = HDFS_BASE + "/gold/live_driver_summary"


def main():
    spark = SparkSession.builder.appName("silver_to_gold_stream").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    logger.info("Reading Silver streaming data from: %s", SILVER_PATH)

    df = spark.read.parquet(SILVER_PATH)

    logger.info("Silver row count: %s", df.count())

    gold_df = (
        df.groupBy("raceid", "driverid")
        .agg(
            F.count("lap").alias("lap_count"),
            F.min("milliseconds").alias("fastest_lap_ms"),
            F.avg("milliseconds").alias("avg_lap_ms"),
            F.max("lap").alias("latest_lap"),
            F.max("kafka_ts").alias("last_event_ts")
        )
        .withColumn("avg_lap_ms", F.round(F.col("avg_lap_ms"), 2))
        .withColumn("ingest_date", F.to_date("last_event_ts"))
    )

    logger.info("Gold summary row count: %s", gold_df.count())
    logger.info("Writing Gold streaming summary to: %s", GOLD_PATH)

    (
        gold_df.write
        .mode("overwrite")
        .partitionBy("ingest_date")
        .parquet(GOLD_PATH)
    )

    logger.info("Streaming Silver to Gold completed successfully")

    spark.stop()


if __name__ == "__main__":
    main()[D[C[D