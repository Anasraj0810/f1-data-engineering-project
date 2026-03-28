from pyspark.sql import SparkSession
from pyspark.sql import functions as F
from pyspark.sql.window import Window

spark = SparkSession.builder.appName("silver_to_gold_stream").getOrCreate()

df = spark.read.parquet("/tmp/anas_proj/silver/live_lap_events")

# Latest lap per driver
latest_lap_df = df.groupBy("raceid", "driverid").agg(
    F.max("lap").alias("latest_lap"),
    F.min("milliseconds").alias("best_lap_ms"),
    F.avg("milliseconds").alias("avg_lap_ms")
)

# Latest position per driver
w = Window.partitionBy("raceid", "driverid").orderBy(F.col("lap").desc(), F.col("event_ts").desc())

latest_position_df = df.withColumn("rn", F.row_number().over(w)) \
    .filter(F.col("rn") == 1) \
    .select(
        "raceid",
        "driverid",
        F.col("position").alias("latest_position")
    )

gold_df = latest_lap_df.join(
    latest_position_df,
    on=["raceid", "driverid"],
    how="left"
)

gold_df.write.mode("overwrite").parquet("/tmp/anas_proj/gold/live_lap_metrics_gold")

print("Streaming gold metrics created successfully")