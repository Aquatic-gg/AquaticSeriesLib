plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "xyz.larkyy.aquaticseries"
version = "1.0"

repositories {
    mavenCentral()
    paperMc()
    spigotMc()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    bgSoftware()
    lumine()
}

dependencies {
    api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    api("io.lumine:Mythic-Dist:5.3.5")
}
