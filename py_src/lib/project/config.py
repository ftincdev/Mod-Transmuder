from pathlib import Path
from py_src.lib.io_utils import json_file

CONFIG_FILE = Path("config.json")

def read() -> dict:
    return json_file.read(CONFIG_FILE)

def write(data: dict) -> None:
    json_file.write(CONFIG_FILE, data)
