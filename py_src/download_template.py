# Download template

import io
import zipfile
import requests
import json
from pathlib import Path

with open("config.json", "r", encoding="utf-8") as file:
    config = json.load(file)

zip_url = config["template_zip_url"]
extract_to = config["transmudation_output"]

extract_path = Path(extract_to)
has_subdir = extract_path.is_dir() and any(
    item.is_dir() for item in extract_path.iterdir()
)
if not has_subdir:
    print("Downloading archive...")
    response = requests.get(zip_url)

    if response.status_code == 200:
        with zipfile.ZipFile(io.BytesIO(response.content)) as zip_file:
            zip_file.extractall(extract_to)
        print(f"The template was successfully downloaded and unpacked into: {extract_to}")
    else:
        print(f"Download error: {response.status_code}")
