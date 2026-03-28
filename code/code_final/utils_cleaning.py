import re
from pyspark.sql import functions as F


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