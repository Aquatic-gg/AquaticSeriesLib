import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "xyz.larkyy.gradletesting"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    spigotMc()
    bgSoftware()
    lumine()
}

dependencies {
    implementation(project(":core:core-api"))
    implementation(project(":farming:farming-api"))
    implementation(project(":farming:farming-plugin"))
    implementation("com.jeff-media:custom-block-data:2.2.2")
    compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.4")
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

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("AquaticSkyblock-${project.version}.jar")
    archiveClassifier.set("plugin")
    dependencies {
        for (it in rootProject.allprojects) {
            val parent = it.parent ?: continue
            if (parent.name == rootProject.name) continue
            include(project(":${parent.name}:${it.name}"))
        }
        include(project(":AquaticSeriesLib"))
        include(dependency("com.jeff-media:custom-block-data"))
    }
}