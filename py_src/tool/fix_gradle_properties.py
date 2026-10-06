# Fix gradle.properties

import re
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

from lib.project import config

project_config = config.read()

transmudation_output = project_config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

gradle_properties = mod_sub_dir / "gradle.properties"

# Read gradle.properties
with open(gradle_properties, "r", encoding="utf-8") as file:
    text = file.read()

text = re.sub(
    r'^[ \t]*loom_version=1\.18-SNAPSHOT',
    'loom_version=1.17-SNAPSHOT',
    text,
    flags=re.M,
)

text = re.sub(
    r'^[ \t]*group=com\.example',
    'group=',
    text,
    flags=re.M,
)

# Write gradle.properties
with open(gradle_properties, "w", encoding="utf-8") as file:
    file.write(text)
