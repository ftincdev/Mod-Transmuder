# Remove splitEnvironmentSourceSets() and sourceSet sourceSets.client
# Even not hardcoded for this template btw probably maybe

import re
import json
from pathlib import Path

# Read config
with open("config.json", "r", encoding="utf-8") as file:
    config = json.load(file)

transmudation_output = config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

build_gradle = mod_sub_dir / "build.gradle"

# Read build.gradle
with open(build_gradle, "r", encoding="utf-8") as file:
    text = file.read()

text = re.sub(
    r'^[ \t]*(?:splitEnvironmentSourceSets\(\)|sourceSet\s+sourceSets\.client)[ \t]*\r?\n',
    '',
    text,
    flags=re.M,
)

# Write build.gradle
with open(build_gradle, "w", encoding="utf-8") as file:
    file.write(text)
