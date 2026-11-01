plugins {
    java
    `java-gradle-plugin`
    id("com.gradleup.shadow") version "9.2.2"
    `maven-publish`
}

group = "io.github.ftincdev.mt"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

configurations {
    create("snapshotDep")
}

configurations.named("implementation") {
    extendsFrom(configurations["snapshotDep"])
}

configurations.named("snapshotDep") {
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("ch.qos.logback:logback-classic:1.5.3")

    implementation("io.github.ftincdev.mttransformer:MT-transformer-1.7.10:0.2.0")
    
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

gradlePlugin {
    plugins {
        create("mtPlugin") {
            id = "io.github.ftincdev.mt"
            implementationClass = "io.github.ftincdev.mt.MTPlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("Mod-Transmuder")
    archiveClassifier.set("all")
    archiveVersion.set("")
    mergeServiceFiles()
}

publishing {
    publications {
        create<MavenPublication>("myPlugin") {
            from(components["shadow"])
        }
    }
}
