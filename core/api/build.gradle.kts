plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "xyz.larkyy.aquaticskyblock"
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
    api("com.bgsoftware","SuperiorSkyblockAPI","2023.3")
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}