# Automated script to launch the "transmudation"

import subprocess
import json
import sys

# Read config
with open("config.json", "r", encoding="utf-8") as file:
    config = json.load(file)

stop_if_fail = config["stop_if_fail"]

subprocess.run([sys.executable, "py_src\\data_setup.py"], check=stop_if_fail)
subprocess.run([sys.executable, "py_src\\download_template.py"], check=stop_if_fail)
subprocess.run([sys.executable, "py_src\\fix_dir_name.py"], check=stop_if_fail)

subprocess.run([sys.executable, "py_src\\remove_split_env.py"], check=stop_if_fail)
subprocess.run([sys.executable, "py_src\\remove_client_env.py"], check=stop_if_fail)

subprocess.run([sys.executable, "py_src\\fix_project_name.py"], check=stop_if_fail)
subprocess.run([sys.executable, "py_src\\fix_project_id.py"], check=stop_if_fail)
