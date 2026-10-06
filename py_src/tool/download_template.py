# Download template

import io
import zipfile
import requests
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

from lib.project import config

project_config = config.read()

zip_url = project_config["template_zip_url"]
extract_to = project_config["transmudation_output"]

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
