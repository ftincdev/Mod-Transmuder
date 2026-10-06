# Fix name of template directory

import json
from pathlib import Path
from py_src.lib.project import config

project_config = config.read()

transmudation_input = project_config["transmudation_input"]
transmudation_output = project_config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_input).iterdir() if item.is_dir())
new_mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

new_mod_sub_dir.rename(new_mod_sub_dir.parent / mod_sub_dir.name)
