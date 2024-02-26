import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "xyz.larkyy.aquaticfarming"
version = "1.0"

repositories {
    mavenCentral()
    paperMc()
    spigotMc()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    lumine()
}

dependencies {
    api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    api(project(":AquaticSeriesLib"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("CorePluginKt")
}