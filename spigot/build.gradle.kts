plugins {
    kotlin("jvm")
}

group = "gg.aquatic.aquaticseries.spigot"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}