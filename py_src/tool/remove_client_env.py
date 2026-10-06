# Remove classes form template

import shutil
import json
from pathlib import Path
from py_src.lib.project import config

config = config.read()

transmudation_output = config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

shutil.rmtree(mod_sub_dir / "src" / "client")
