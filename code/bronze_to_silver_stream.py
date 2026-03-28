from pyspark.sql import SparkSession
from pyspark.sql import functions as F

spark = SparkSession.builder.appName("bronze_to_silver_stream").getOrCreate()

df = spark.read.parquet("/tmp/anas_proj/bronze/live_lap_events_raw")

df_clean = df.select(
    F.col("raceid").cast("int").alias("raceid"),
    F.col("driverid").cast("int").alias("driverid"),
    F.col("lap").cast("int").alias("lap"),
    F.col("position").cast("int").alias("position"),
    F.col("lap_time").alias("lap_time"),
    F.col("milliseconds").cast("int").alias("milliseconds"),
    F.col("event_ts").alias("event_ts")
).dropna(subset=["raceid", "driverid", "lap"]) \
 .dropDuplicates(["raceid", "driverid", "lap"])

df_clean.write.mode("overwrite").parquet("/tmp/anas_proj/silver/live_lap_events")

print("Streaming data cleaned into silver successfully")
