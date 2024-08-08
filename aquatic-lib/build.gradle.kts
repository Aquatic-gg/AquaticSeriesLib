group = "gg.aquatic.aquaticseries.lib"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation(project(":core"))
    implementation(project(":paper"))
    implementation(project(":spigot"))
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

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("AquaticLib-${project.version}.jar")
    archiveClassifier.set("plugin")
    dependencies {
        include(project(":spigot"))
        include(project(":paper"))
        include(project(":core"))
        include(dependency("com.jeff-media:custom-block-data:2.2.2"))
    }
}