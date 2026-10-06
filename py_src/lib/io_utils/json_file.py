import json
from pathlib import Path
from typing import Any

def read(path: str | Path) -> Any:
    """Read a JSON file and return its data without validation"""

    with open(path, "r", encoding="utf-8") as file:
        data = json.load(file)

    return data

def write(path: str | Path, data: Any) -> None:
    """Write a JSON file, overwriting if it exists."""

    with open(path, "w", encoding="utf-8") as file:
        json.dump(data, file, ensure_ascii=False, indent=4)
