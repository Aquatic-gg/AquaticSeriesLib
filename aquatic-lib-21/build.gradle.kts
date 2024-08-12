plugins {
    `maven-publish`
}

val maven_username: String by rootProject.extra
val maven_password: String by rootProject.extra

group = "gg.aquatic.aquaticseries"

repositories {
    mavenCentral()
}


dependencies {
    implementation(project(":aquatic-lib"))
    implementation(project(":core"))
    compileOnly("org.spigotmc:spigot-api:1.20.5-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("AquaticLib-${project.version}.jar")
    archiveClassifier.set("all")
    dependencies {
        include(project(":spigot"))
        include(project(":paper"))
        include(project(":core"))
        include(project(":aquatic-lib"))
        include(dependency("com.jeff-media:custom-block-data:2.2.2"))
    }
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
            artifactId = "aquaticlib-21"
            version = "${project.version}"
            from(components["java"])
        }
    }
}
