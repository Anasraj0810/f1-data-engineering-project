from pyspark.sql import functions as F


def test_gold_join_logic(spark):
    drivers_df = spark.createDataFrame([
        (1, "Lewis", "Hamilton", "British")
    ], ["driverid", "forename", "surname", "nationality"])

    constructors_df = spark.createDataFrame([
        (10, "Mercedes", "German")
    ], ["constructorid", "name", "nationality"])

    races_df = spark.createDataFrame([
        (100, "British GP", 2024, 12, "2024-07-07")
    ], ["raceid", "name", "year", "round", "date"])

    results_df = spark.createDataFrame([
        (1000, 100, 1, 10, 1, 1, 1, 25.0, 52, 40, 220.5)
    ], [
        "resultid", "raceid", "driverid", "constructorid",
        "grid", "position", "positionorder", "points",
        "laps", "fastestlap", "fastestlapspeed"
    ])

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

    gold_df = results_df.alias("res") \
        .join(races_dim.alias("rac"), "raceid", "left") \
        .join(drivers_dim.alias("drv"), "driverid", "left") \
        .join(constructors_dim.alias("con"), "constructorid", "left")

    rows = gold_df.collect()
    assert len(rows) == 1

    row = rows[0].asDict()
    assert row["race_name"] == "British GP"
    assert row["driver_name"] == "Lewis Hamilton"
    assert row["constructor_name"] == "Mercedes"