from utils_cleaning import clean_column_name, basic_clean


def test_clean_column_name():
    assert clean_column_name(" Driver ID ") == "driver_id"
    assert clean_column_name("race-id") == "race_id"
    assert clean_column_name("fastestLapSpeed") == "fastestlapspeed"


def test_basic_clean_removes_blank_and_duplicates(spark):
    data = [
        (" Lewis ", "", "44"),
        ("Lewis", "", "44"),
        ("\\N", "British", "44")
    ]

    df = spark.createDataFrame(data, ["Driver Name", "Nationality", "Number"])
    cleaned = basic_clean(df)

    assert "driver_name" in cleaned.columns
    assert "nationality" in cleaned.columns
    assert "number" in cleaned.columns

    rows = cleaned.collect()
    assert len(rows) == 2