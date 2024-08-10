group = "gg.aquatic.aquaticseries"
version = "1.0"

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
    archiveClassifier.set("plugin")
    dependencies {
        include(project(":spigot"))
        include(project(":paper"))
        include(project(":core"))
        include(project(":aquatic-lib"))
        include(dependency("com.jeff-media:custom-block-data:2.2.2"))
    }
}
