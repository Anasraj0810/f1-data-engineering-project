from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("postgres_to_bronze_stream") \
    .config(
        "spark.jars",
        "/home/ec2-user/250226batch/Anasraj/f1_proj_v2/code/postgresql-42.7.10.jar"
    ) \
    .getOrCreate()

jdbc_url = "jdbc:postgresql://13.42.152.118:5432/testdb"

properties = {
    "user": "admin",
    "password": "admin123",
    "driver": "org.postgresql.Driver"
}

df_raw = spark.read.jdbc(
    url=jdbc_url,
    table="anas.live_lap_events_raw",
    properties=properties
)

df_raw.write.mode("overwrite").parquet("/tmp/anas_proj/bronze/live_lap_events_raw")

print("Streaming raw data moved from PostgreSQL to HDFS bronze successfully")