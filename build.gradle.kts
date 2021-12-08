import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "net.kactus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))

    //Server dependencies
    implementation("io.netty:netty-all:4.1.68.Final")
    implementation("org.reflections:reflections:0.9.12")
}

tasks.test {
    useJUnit()
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}