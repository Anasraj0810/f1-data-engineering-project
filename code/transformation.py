from pyspark.sql import SparkSession
from pyspark.sql import functions as F

spark = SparkSession.builder \
    .appName("silver_to_gold_full") \
    .getOrCreate()

# Read silver tables
drivers_df = spark.read.parquet("/tmp/anas_proj2/silver/drivers")
constructors_df = spark.read.parquet("/tmp/anas_proj2/silver/constructors")
races_df = spark.read.parquet("/tmp/anas_proj2/silver/races")
results_df = spark.read.parquet("/tmp/anas_proj2/silver/results")

# Prepare dimensions
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

# Build gold table
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

# Write to HDFS Gold
race_results_gold.write \
    .mode("overwrite") \
    .partitionBy("year") \
    .parquet("/tmp/anas_proj2/gold/race_results")

print("race_results_gold written to HDFS gold successfully")