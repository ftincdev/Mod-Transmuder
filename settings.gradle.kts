plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val localFile = file("local.properties")
var transformerPath: String? = null

if (localFile.exists()) {
    localFile.readLines().forEach { line ->
        if (line.startsWith("mt.transformerPath=")) {
            transformerPath = line.substringAfter("mt.transformerPath=").trim()
        }
    }
}

if (transformerPath != null && file(transformerPath!!).exists()) {
    includeBuild(transformerPath!!) {
        dependencySubstitution {
            substitute(module("io.github.ftincdev.mttransformer:MT-transformer-1.7.10")).using(project(":"))
        }
    }
    println("[MT-build] Using local transformer build from $transformerPath")
}

rootProject.name = "Mod-Transmuder"
