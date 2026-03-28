from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("gold_to_hive_partitioned") \
    .enableHiveSupport() \
    .getOrCreate()

spark.sql("CREATE DATABASE IF NOT EXISTS anasdb")

spark.sql("DROP TABLE IF EXISTS anasdb.race_results_gold_hive")

spark.sql("""
CREATE EXTERNAL TABLE anasdb.race_results_gold_hive (
    resultid INT,
    raceid INT,
    race_name STRING,
    round INT,
    race_date DATE,
    driverid INT,
    driver_name STRING,
    driver_nationality STRING,
    constructorid INT,
    constructor_name STRING,
    constructor_nationality STRING,
    grid INT,
    position INT,
    positionorder INT,
    points DOUBLE,
    laps INT,
    fastestlap INT,
    fastestlapspeed DOUBLE
)
PARTITIONED BY (year INT)
STORED AS PARQUET
LOCATION '/tmp/anas_proj/gold/race_results_gold'
""")

spark.sql("MSCK REPAIR TABLE anasdb.race_results_gold_hive")

print("Partitioned Hive table created: anasdb.race_results_gold_hive")

spark.sql("SELECT * FROM anasdb.race_results_gold_hive LIMIT 10").show(truncate=False)