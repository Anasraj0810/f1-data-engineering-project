from pyspark.sql import SparkSession
from pyspark.sql import functions as F
import re

spark = SparkSession.builder.appName("bronze_to_silver_full").getOrCreate()

# -----------------------------
# Read bronze data
# -----------------------------
drivers_df = spark.read.csv("/tmp/anas_proj2/bronze/drivers/full", header=False, inferSchema=True) \
    .toDF("driverid", "driverref", "number", "code", "forename", "surname", "dob", "nationality", "url")

constructors_df = spark.read.csv("/tmp/anas_proj2/bronze/constructors/full", header=False, inferSchema=True) \
    .toDF("constructorid", "constructorref", "name", "nationality", "url")

races_df = spark.read.csv("/tmp/anas_proj2/bronze/races/full/races_initial.csv", header=True, inferSchema=True)

results_df = spark.read.csv("/tmp/anas_proj2/bronze/results/full/results_initial.csv", header=True, inferSchema=True)

# -----------------------------
# Cleaning functions
# -----------------------------
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

    for col_name, dtype in df.dtypes:
        if dtype == "string":
            df = df.withColumn(
                col_name,
                F.when(F.col(col_name) == "", None).otherwise(F.col(col_name))
            )

    for col_name, dtype in df.dtypes:
        if dtype == "string":
            df = df.withColumn(
                col_name,
                F.when(F.col(col_name) == r"\N", None).otherwise(F.col(col_name))
            )

    df = df.dropna(how="all").dropDuplicates()

    return df

# -----------------------------
# Clean drivers
# -----------------------------
drivers_cleaned_df = basic_clean(drivers_df) \
    .withColumn("driverid", F.col("driverid").cast("int")) \
    .withColumn("number", F.col("number").cast("int")) \
    .withColumn(
        "dob",
        F.when(
            F.col("dob").rlike(r"^\d{2}/\d{2}/\d{4}$"),
            F.to_date(F.col("dob"), "dd/MM/yyyy")
        ).when(
            F.col("dob").rlike(r"^\d{4}-\d{2}-\d{2}$"),
            F.to_date(F.col("dob"), "yyyy-MM-dd")
        ).otherwise(None)
    ) \
    .filter(F.col("driverid").isNotNull())

# -----------------------------
# Clean constructors
# -----------------------------
constructors_cleaned_df = basic_clean(constructors_df) \
    .withColumn("constructorid", F.col("constructorid").cast("int")) \
    .filter(F.col("constructorid").isNotNull())

# -----------------------------
# Clean races
# -----------------------------
races_cleaned_df = basic_clean(races_df) \
    .withColumn("raceid", F.col("raceid").cast("int")) \
    .withColumn("year", F.col("year").cast("int")) \
    .withColumn("round", F.col("round").cast("int")) \
    .withColumn("circuitid", F.col("circuitid").cast("int")) \
    .withColumn("date", F.to_date(F.col("date"), "yyyy-MM-dd")) \
    .filter(F.col("raceid").isNotNull())

# -----------------------------
# Clean results
# -----------------------------
results_cleaned_df = basic_clean(results_df) \
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

# -----------------------------
# Write to silver
# -----------------------------

drivers_cleaned_df.coalesce(1).write.mode("overwrite").parquet("/tmp/anas_proj2/silver/drivers")

constructors_cleaned_df.coalesce(1).write.mode("overwrite").parquet("/tmp/anas_proj2/silver/constructors")

races_cleaned_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/races")

results_partitioned_df = results_cleaned_df.join(
    races_cleaned_df.select("raceid", "year"),
    on="raceid",
    how="left"
)

results_partitioned_df.write.mode("overwrite").partitionBy("year").parquet("/tmp/anas_proj2/silver/results")