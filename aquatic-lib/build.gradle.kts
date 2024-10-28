plugins {
    `maven-publish`
    kotlin("plugin.serialization") version "2.0.0"
}

val maven_username: String by rootProject.extra
val maven_password: String by rootProject.extra

group = "gg.aquatic.aquaticseries"

repositories {
    mavenCentral()
}

val nmsVersion = "1.0.35"

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    implementation(project(":core"))
    implementation(project(":paper"))
    implementation(project(":spigot"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
    compileOnly("com.zaxxer:HikariCP:5.1.0")
    compileOnly("gg.aquatic:AEAPI:1.0")
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_17_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_18_2:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_19_4:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_4:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_20_6:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_21:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_21_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
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
    archiveFileName.set("AquaticLib-${project.version}.jar")
    archiveClassifier.set("all")
    dependencies {
        include(project(":spigot"))
        include(project(":paper"))
        include(project(":core"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_17_1"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_18_2"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_19_4"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_1"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_4"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_20_6"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_21"))
        include(dependency("gg.aquatic.aquaticseries.nms:NMS_v1_21_1"))
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
            artifactId = "aquaticlib"
            version = "${project.version}"
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}