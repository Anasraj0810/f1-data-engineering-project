import logging
import os
from datetime import datetime


def get_logger(name: str) -> logging.Logger:
    os.makedirs("logs", exist_ok=True)

    logger = logging.getLogger(name)
    logger.setLevel(logging.INFO)

    if not logger.handlers:
        formatter = logging.Formatter(
            "%(asctime)s | %(levelname)s | %(name)s | %(message)s"
        )

        log_file = datetime.now().strftime("logs/pipeline_%Y%m%d_%H%M%S.log")

        file_handler = logging.FileHandler(log_file)
        file_handler.setFormatter(formatter)

        stream_handler = logging.StreamHandler()
        stream_handler.setFormatter(formatter)

        logger.addHandler(file_handler)
        logger.addHandler(stream_handler)

    return logger