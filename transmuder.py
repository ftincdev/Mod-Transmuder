# Automated script to launch the "transmudation"

import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).parent
PY_SRC = ROOT / "py_src"
TOOLS_PATH = PY_SRC / "tool"

sys.path.insert(0, str(PY_SRC))

from py_src.lib.project import config

project_config = config.read()

stop_if_fail = project_config["stop_if_fail"]

subprocess.run([sys.executable, TOOLS_PATH / "data_setup.py"], check=stop_if_fail)
subprocess.run([sys.executable, TOOLS_PATH / "download_template.py"], check=stop_if_fail)
subprocess.run([sys.executable, TOOLS_PATH / "fix_dir_name.py"], check=stop_if_fail)

subprocess.run([sys.executable, TOOLS_PATH / "remove_split_env.py"], check=stop_if_fail)
subprocess.run([sys.executable, TOOLS_PATH / "remove_client_env.py"], check=stop_if_fail)

subprocess.run([sys.executable, TOOLS_PATH / "fix_project_name.py"], check=stop_if_fail)
subprocess.run([sys.executable, TOOLS_PATH / "fix_project_id.py"], check=stop_if_fail)
