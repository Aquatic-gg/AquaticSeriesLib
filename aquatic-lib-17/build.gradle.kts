group = "gg.aquatic.aquaticseries"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":aquatic-lib"))
    implementation(project(":core"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
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