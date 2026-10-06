# Fix project name in src

import json
from pathlib import Path
from py_src.lib.project import config
from py_src.lib.project import data

project_config = config.read()
project_data = data.read()

transmudation_output = project_config["transmudation_output"]
project_name = project_data["project_name"]

new_mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

def fix_mod_json():
    # Read mod json
    with open(new_mod_sub_dir / "src" / "main" / "resources" / "fabric.mod.json", "r", encoding="utf-8") as file:
        data = json.load(file)

    data["name"] = project_name

    # Write mod json
    with open(new_mod_sub_dir / "src" / "main" / "resources" / "fabric.mod.json", "w", encoding="utf-8") as file:
        json.dump(data, file, ensure_ascii=False, indent=4)

fix_mod_json()
