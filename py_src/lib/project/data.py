from pathlib import Path
from lib.io_utils import json_file

DATA_FILE = Path("data.json")

def read() -> dict:
    return json_file.read(DATA_FILE)

def write(data: dict) -> None:
    json_file.write(DATA_FILE, data)
