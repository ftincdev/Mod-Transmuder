# Remove splitEnvironmentSourceSets() and sourceSet sourceSets.client
# Even not hardcoded for this template btw probably maybe

import re
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent.parent))

from lib.project import config
from lib.io_utils import text_file

project_config = config.read()

transmudation_output = project_config["transmudation_output"]

mod_sub_dir = next(item for item in Path(transmudation_output).iterdir() if item.is_dir())

build_gradle = mod_sub_dir / "build.gradle"

text = text_file.read(build_gradle)

text = re.sub(
    r'^[ \t]*(?:splitEnvironmentSourceSets\(\)|sourceSet\s+sourceSets\.client)[ \t]*\r?\n',
    '',
    text,
    flags=re.M,
)

text_file.write(build_gradle, text)
