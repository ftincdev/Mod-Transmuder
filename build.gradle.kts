plugins {
    java
    application
    id("com.gradleup.shadow") version "9.2.2"
}

group = "io.github.ftincdev.ftincdev.mt"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("ch.qos.logback:logback-classic:1.5.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

application {
    mainClass.set("io.github.ftincdev.mt.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("MT")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
}
