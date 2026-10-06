# Fix project id in all mod src

import json
from pathlib import Path
from py_src.lib.project import config
from py_src.lib.project import data
from py_src.lib.io_utils import json_file

project_config = config.read()
project_data = data.read()

transmudation_output = project_config["transmudation_output"]

project_id = project_data["project_id"]

new_mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

def fix_mod_json():
    mod_json_path = new_mod_sub_dir / "src" / "main" / "resources" / "fabric.mod.json"

    mod_data = json_file.read(mod_json_path)

    mod_data["id"] = project_id

    json_file.write(mod_json_path, mod_data)

fix_mod_json()
