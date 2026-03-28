from pyspark.sql import SparkSession
from pyspark.sql import functions as F
import re

spark = SparkSession.builder.appName("incremental_bronze_to_silver").getOrCreate()

def clean_column_name(col_name):
    col_name = col_name.strip().lower()
    col_name = re.sub(r"[^a-zA-Z0-9]+", "_", col_name)
    col_name = re.sub(r"_+", "_", col_name)
    return col_name.strip("_")

def basic_clean(df):
    for c in df.columns:
        df = df.withColumnRenamed(c, clean_column_name(c))

    for col_name, dtype in df.dtypes:
        if dtype == "string":
            df = df.withColumn(col_name, F.trim(F.col(col_name)))
            df = df.withColumn(
                col_name,
                F.when(F.col(col_name) == "", None).otherwise(F.col(col_name))
            )
            df = df.withColumn(
                col_name,
                F.when(F.col(col_name) == r"\N", None).otherwise(F.col(col_name))
            )

    df = df.dropna(how="all").dropDuplicates()
    return df

# Read incremental bronze CSVs
races_inc_df = spark.read.csv(
    "/tmp/anas_proj/bronze/races_incremental.csv",
    header=True,
    inferSchema=True
)

results_inc_df = spark.read.csv(
    "/tmp/anas_proj/bronze/results_incremental.csv",
    header=True,
    inferSchema=True
)

# Clean races incremental
races_inc_clean = basic_clean(races_inc_df) \
    .withColumn("raceid", F.col("raceid").cast("int")) \
    .withColumn("year", F.col("year").cast("int")) \
    .withColumn("round", F.col("round").cast("int")) \
    .withColumn("circuitid", F.col("circuitid").cast("int")) \
    .withColumn("date", F.to_date(F.col("date"), "yyyy-MM-dd")) \
    .filter(F.col("raceid").isNotNull())

# Clean results incremental
results_inc_clean = basic_clean(results_inc_df) \
    .withColumn("resultid", F.col("resultid").cast("int")) \
    .withColumn("raceid", F.col("raceid").cast("int")) \
    .withColumn("driverid", F.col("driverid").cast("int")) \
    .withColumn("constructorid", F.col("constructorid").cast("int")) \
    .withColumn("number", F.col("number").cast("int")) \
    .withColumn("grid", F.col("grid").cast("int")) \
    .withColumn("position", F.col("position").cast("int")) \
    .withColumn("positionorder", F.col("positionorder").cast("int")) \
    .withColumn("points", F.col("points").cast("double")) \
    .withColumn("laps", F.col("laps").cast("int")) \
    .withColumn("milliseconds", F.col("milliseconds").cast("bigint")) \
    .withColumn("fastestlap", F.col("fastestlap").cast("int")) \
    .withColumn("rank", F.col("rank").cast("int")) \
    .withColumn("fastestlapspeed", F.col("fastestlapspeed").cast("double")) \
    .filter(
        F.col("raceid").isNotNull() &
        F.col("driverid").isNotNull() &
        F.col("constructorid").isNotNull()
    )

# Write incremental silver
races_inc_clean.write.mode("overwrite").parquet("/tmp/anas_proj/silver/races_incremental")
results_inc_clean.write.mode("overwrite").parquet("/tmp/anas_proj/silver/results_incremental")
