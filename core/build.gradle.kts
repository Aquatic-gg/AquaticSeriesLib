plugins {
    kotlin("plugin.serialization") version "2.0.0"
    `maven-publish`
}


val maven_username: String by rootProject.extra
val maven_password: String by rootProject.extra

group = "gg.aquatic.aquaticseries.core"


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
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

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    artifacts {
        archives(sourcesJar)
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("AquaticLib-Core-${project.version}.jar")
    archiveClassifier.set("all")

    exclude("kotlin/**")
    exclude("kotlinx/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
}

publishing {
    repositories {
        maven {
            name = "aquaticRepository"
            url = uri("https://repo.nekroplex.com/releases")
            credentials {
                username = maven_username
                password = maven_password
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "gg.aquatic.aquaticseries"
            artifactId = "aquaticlib-core"
            version = "${project.version}"
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}