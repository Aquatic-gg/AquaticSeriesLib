import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
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
    maven("https://repo.oraxen.com/releases")
    maven("https://jitpack.io")
}

dependencies {
    api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    api("io.lumine:Mythic-Dist:5.3.5")
    implementation("com.jeff-media:custom-block-data:2.2.2")
    compileOnly("io.th0rgal:oraxen:1.171.0")
    compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.2-beta-r3-b")
    compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
