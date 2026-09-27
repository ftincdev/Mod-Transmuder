# Automatic data setup. Whenever...

import re
import json
from pathlib import Path

# Read config
with open("config.json", "r", encoding="utf-8") as file:
    config = json.load(file)

transmudation_input = config["transmudation_input"]
rewrite_data = config["rewrite_data"]

if rewrite_data:
    # Data init
    data = {
        "project_name": "",
        "project_id": ""
    }
else:
    # Read data
    with open("data.json", "r", encoding="utf-8") as file:
        data = json.load(file)


mod_sub_dir = next(item for item in Path(transmudation_input).iterdir() if item.is_dir())

# Parse properties
# Terrible part, vibe-coded!!!
def parse_gradle_properties(path):
    props = {}
    with open(path, encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if not line or line.startswith("#") or line.startswith("!"):
                continue
            m = re.match(r"^([^=:\s]+)\s*[=:]\s*(.*)$", line)
            if not m:
                continue
            key, value = m.group(1), m.group(2)
            value = re.sub(r"\\(.)", r"\1", value)
            props[key] = value
    return props

props = parse_gradle_properties(mod_sub_dir / "gradle.properties")

# Trying to extract project name
def extract_project_name():
    project_name = props["modName"]

    print("Extracted project name: " + project_name)

    return project_name

# Trying to extract project id
def extract_project_id():
    project_id = props["modId"]

    print("Extracted project id: " + project_id)

    return project_id

if data["project_name"] == "":
    data["project_name"] = extract_project_name()

if data["project_id"] == "":
    data["project_id"] = extract_project_id()

# Write data
with open("data.json", "w", encoding="utf-8") as file:
    json.dump(data, file, ensure_ascii=False, indent=4)
