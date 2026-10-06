from pathlib import Path
from py_src.lib.io_utils import raw_text

DATA_FILE = Path("data.json")

def read() -> dict:
    return raw_text.read(DATA_FILE)

def write(data: dict) -> None:
    raw_api.write(DATA_FILE, data)
