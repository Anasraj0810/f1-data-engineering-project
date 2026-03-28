from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("gold_to_postgres_stream") \
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

df_gold = spark.read.parquet("/tmp/anas_proj/gold/live_lap_metrics_gold")

df_gold.write \
    .mode("overwrite") \
    .option("truncate", "true") \
    .jdbc(
        url=jdbc_url,
        table="anas.live_lap_metrics_gold",
        properties=properties
    )

print("Streaming gold metrics pushed to PostgreSQL successfully")
