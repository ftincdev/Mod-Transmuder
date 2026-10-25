# Pipeline

All these steps are automatic and logged in the console. Step 2 works only in runtime mode. Other steps work in Gradle plugin mode.

## Step 1. Prepare template and libs
1. Download libs (`MT-layer-VERSION`, `MT-fake-API-VERSION`, etc.).
2. Download the [template](https://github.com/ftincdev/MT-template).
3. Clean the template.
4. Put the template in the cache.

## Step 2. Decompile mod (runtime only)
1. Check all mods in the `mods` folder.
2. Decompile incompatible mods.

## Step 3. Apply transformer
1. Use `MT-transformer-VERSION` (huge step, check MT-transformer docs).
2. MT-transformer moves files to the output directory.
3. MT-transformer applies other transformers.

## Step 4. Compile mod
1. Compile the mod.
2. Put the mod in the `mods` folder (runtime only).
