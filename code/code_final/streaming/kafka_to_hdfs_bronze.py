import logging
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, from_json
from pyspark.sql.types import StructType, StructField, IntegerType, StringType

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [CONSUMER] %(message)s",
    datefmt="%H:%M:%S"
)

logger = logging.getLogger(__name__)

HDFS_BASE = "hdfs://ip-172-31-3-251.eu-west-2.compute.internal:8020/tmp/anas_proj2"
BRONZE_PATH = HDFS_BASE + "/bronze/live_lap_times_powerbi_demo"
CHECKPOINT_PATH = HDFS_BASE + "/checkpoints/live_lap_times_powerbi_demo"

KAFKA_TOPIC = "f1_lap_times"
KAFKA_BOOTSTRAP_SERVERS = (
    "ip-172-31-6-42.eu-west-2.compute.internal:9092,"
    "ip-172-31-3-85.eu-west-2.compute.internal:9092"
)

F1_SCHEMA = StructType([
    StructField("raceid", IntegerType(), True),
    StructField("driverid", IntegerType(), True),
    StructField("lap", IntegerType(), True),
    StructField("position", IntegerType(), True),
    StructField("lap_time", StringType(), True),
    StructField("milliseconds", IntegerType(), True)
])


def create_spark_session():
    return (
        SparkSession.builder
        .appName("F1KafkaToHDFSBronzePowerBIVerySlow")
        .getOrCreate()
    )


def process_batch(batch_df, batch_id):
    batch_df = batch_df.persist()

    row_count = batch_df.count()

    if row_count > 0:
        logger.info("========== BATCH %s | ROWS: %s ==========", batch_id, row_count)
        print(f"\n========== BATCH {batch_id} | ROWS: {row_count} ==========")

        rows = (
            batch_df
            .orderBy("kafka_ts")
            .select(
                "raceid",
                "driverid",
                "lap",
                "position",
                "lap_time",
                "milliseconds",
                "kafka_ts"
            )
            .collect()
        )

        for r in rows:
            print(
                f"[RECEIVED] "
                f"raceid={r['raceid']} "
                f"driverid={r['driverid']} "
                f"lap={r['lap']} "
                f"position={r['position']} "
                f"lap_time={r['lap_time']} "
                f"ms={r['milliseconds']} "
                f"kafka_ts={r['kafka_ts']}"
            )

        (
            batch_df.write
            .mode("append")
            .parquet(BRONZE_PATH)
        )

        logger.info("Batch %s written to %s", batch_id, BRONZE_PATH)
    else:
        print(f"\n========== BATCH {batch_id} | NO DATA ==========")

    batch_df.unpersist()


def main():
    spark = create_spark_session()
    spark.sparkContext.setLogLevel("WARN")

    logger.info("Reading from Kafka topic: %s", KAFKA_TOPIC)
    logger.info("Bootstrap servers: %s", KAFKA_BOOTSTRAP_SERVERS)
    logger.info("Writing Bronze stream to: %s", BRONZE_PATH)

    raw_stream = (
        spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS)
        .option("subscribe", KAFKA_TOPIC)
        .option("startingOffsets", "latest")
        .option("failOnDataLoss", "false")
        .load()
    )

    parsed_stream = (
        raw_stream
        .withColumn("json_string", col("value").cast("string"))
        .withColumn("data", from_json(col("json_string"), F1_SCHEMA))
        .select(
            col("data.raceid").alias("raceid"),
            col("data.driverid").alias("driverid"),
            col("data.lap").alias("lap"),
            col("data.position").alias("position"),
            col("data.lap_time").alias("lap_time"),
            col("data.milliseconds").alias("milliseconds"),
            col("timestamp").alias("kafka_ts")
        )
    )

    query = (
        parsed_stream.writeStream
        .foreachBatch(process_batch)
        .outputMode("append")
        .option("checkpointLocation", CHECKPOINT_PATH)
        .trigger(processingTime="5 seconds")
        .start()
    )

    logger.info("Kafka consumer is running. Printing rows and writing to HDFS.")
    query.awaitTermination()


if __name__ == "__main__":
    main()