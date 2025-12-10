plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(kotlin("script-runtime"))
    implementation("com.google.ortools:ortools-java:9.14.6206")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}