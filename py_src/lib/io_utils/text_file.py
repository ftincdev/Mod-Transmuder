from pathlib import Path

def read(path: str | Path) -> str:
    """Read a raw text file and return its raw text"""

    with open(path, "r", encoding="utf-8") as file:
        text = file.read()

    return text

def write(path: str | Path, text: str) -> None:
    """Write text to a file, overwriting if it exists."""

    with open(path, "w", encoding="utf-8") as file:
        file.write(text)
