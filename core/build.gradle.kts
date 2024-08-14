plugins {
    kotlin("plugin.serialization") version "2.0.0"
}

group = "gg.aquatic.aquaticseries.core"


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
}