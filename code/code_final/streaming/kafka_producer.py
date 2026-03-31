import csv
import json
import time
import logging
from collections import defaultdict
from kafka import KafkaProducer

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [PRODUCER] %(message)s",
    datefmt="%H:%M:%S"
)

logger = logging.getLogger(__name__)

CSV_PATH = "/home/ec2-user/250226batch/Anasraj/f1_proj_v2/code/code_final/streaming/lap_times_live.csv"
TOPIC_NAME = "f1_lap_times"

BOOTSTRAP_SERVERS = [
    "ip-172-31-6-42.eu-west-2.compute.internal:9092",
    "ip-172-31-3-85.eu-west-2.compute.internal:9092"
]

# Very slow demo mode:
# one message every 10 seconds
FIXED_SLEEP_SECONDS = 10


def format_row(row):
    return {
        "raceid": int(row["raceId"]),
        "driverid": int(row["driverId"]),
        "lap": int(row["lap"]),
        "position": int(row["position"]),
        "lap_time": row["time"],
        "milliseconds": int(row["milliseconds"])
    }


def load_live_like_events(csv_path):
    """
    Reorders the CSV into a more live-like sequence:
    - sort each driver's laps in order
    - compute cumulative race time per driver
    - globally sort by simulated event time
    """
    grouped = defaultdict(list)

    with open(csv_path, mode="r", encoding="utf-8") as file:
        reader = csv.DictReader(file)

        for row in reader:
            record = format_row(row)
            grouped[(record["raceid"], record["driverid"])].append(record)

    events = []

    for (raceid, driverid), laps in grouped.items():
        laps.sort(key=lambda x: x["lap"])

        sim_time_ms = 0
        for lap_row in laps:
            sim_time_ms += lap_row["milliseconds"]

            event = dict(lap_row)
            event["sim_time_ms"] = sim_time_ms
            events.append(event)

    events.sort(
        key=lambda x: (
            x["raceid"],
            x["sim_time_ms"],
            x["lap"],
            x["position"],
            x["driverid"]
        )
    )

    return events


def stream_data():
    producer = KafkaProducer(
        bootstrap_servers=BOOTSTRAP_SERVERS,
        value_serializer=lambda v: json.dumps(v).encode("utf-8"),
        acks="all",
        retries=3,
        max_block_ms=5000
    )

    events = load_live_like_events(CSV_PATH)

    logger.info("Streaming started -> topic: %s", TOPIC_NAME)
    logger.info("Bootstrap servers: %s", BOOTSTRAP_SERVERS)
    logger.info("Very slow demo mode enabled")
    logger.info("Fixed delay between messages: %s seconds", FIXED_SLEEP_SECONDS)
    logger.info("Total events to send: %s", len(events))
    logger.info("-" * 60)

    count = 0

    for event in events:
        payload = {
            "raceid": event["raceid"],
            "driverid": event["driverid"],
            "lap": event["lap"],
            "position": event["position"],
            "lap_time": event["lap_time"],
            "milliseconds": event["milliseconds"]
        }

        producer.send(TOPIC_NAME, value=payload).get(timeout=10)
        count += 1

        print(
            f"[{count:>4}] SENT -> "
            f"raceid={payload['raceid']} "
            f"driverid={payload['driverid']} "
            f"lap={payload['lap']} "
            f"position={payload['position']} "
            f"lap_time={payload['lap_time']} "
            f"lap_ms={payload['milliseconds']}"
        )

        if count < len(events):
            print(f"[WAIT] sleeping {FIXED_SLEEP_SECONDS} seconds...\n")
            time.sleep(FIXED_SLEEP_SECONDS)

    producer.flush()
    producer.close()
    logger.info("Done. %s messages sent.", count)


if __name__ == "__main__":
    stream_data()