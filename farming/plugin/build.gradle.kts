plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "xyz.larkyy.aquaticfarming"
version = "1.0"

repositories {
    mavenCentral()
    spigotMc()
    lumine()
}

dependencies {
    implementation(project(":farming:farming-api"))
    api("com.jeff-media:custom-block-data:2.2.2")
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