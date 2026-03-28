from pyspark.sql import SparkSession
from pyspark.sql import functions as F

spark = SparkSession.builder \
    .appName("incremental_silver_to_gold") \
    .config("spark.jars", "/home/ec2-user/250226batch/Anasraj/f1_proj_v2/code/postgresql-42.7.10.jar") \
    .getOrCreate()

# Read silver dimensions
drivers_df = spark.read.parquet("/tmp/anas_proj/silver/drivers")
constructors_df = spark.read.parquet("/tmp/anas_proj/silver/constructors")

# Read incremental silver facts
races_inc_df = spark.read.parquet("/tmp/anas_proj/silver/races_incremental")
results_inc_df = spark.read.parquet("/tmp/anas_proj/silver/results_incremental")

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

races_dim = races_inc_df.select(
    "raceid",
    F.col("name").alias("race_name"),
    "year",
    "round",
    F.col("date").alias("race_date")
)

results_fact = results_inc_df.select(
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

gold_new = results_fact.alias("res") \
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

# Append to HDFS gold
gold_new.write \
    .mode("append") \
    .partitionBy("year") \
    .parquet("/tmp/anas_proj/gold/race_results_gold")

# Append to PostgreSQL
jdbc_url = "jdbc:postgresql://13.42.152.118:5432/testdb"

connection_properties = {
    "user": "admin",
    "password": "admin123",
    "driver": "org.postgresql.Driver"
}

gold_new.write \
    .mode("append") \
    .jdbc(
        url=jdbc_url,
        table="anas.race_results_gold",
        properties=connection_properties
    )

