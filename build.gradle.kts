import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.6.10"
    application
}

group = "net.propromp"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.Minestom:Minestom:fbd462a6ee")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("net.propromp.stepsforidiots.StepsForIdiotsKt")
}