# Fix name of template directory

import json
from pathlib import Path

# Read config
with open("config.json", "r", encoding="utf-8") as file:
    config = json.load(file)

transmudation_input = config["transmudation_input"]
transmudation_output = config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_input).iterdir() if item.is_dir())
new_mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

new_mod_sub_dir.rename(new_mod_sub_dir.parent / mod_sub_dir.name)
