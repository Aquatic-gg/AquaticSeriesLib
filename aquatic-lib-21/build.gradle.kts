plugins {
    `maven-publish`
}

val maven_username: String by rootProject.extra
val maven_password: String by rootProject.extra

val nmsVersion = "1.0.20"

group = "gg.aquatic.aquaticseries"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.nekroplex.com/releases")
    }
}


dependencies {
    implementation(project(":aquatic-lib"))
    implementation(project(":core"))
    implementation(project(":display-entity"))
    implementation(project(":display-entity-extended"))
    compileOnly("org.spigotmc:spigot-api:1.20.5-R0.1-SNAPSHOT")
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_21_1:1.0.1") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_17_1:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_18_2:$nmsVersion") {
        exclude("gg.aquatic.aquaticseries.nms", "AquaticNMS")
    }
    implementation("gg.aquatic.aquaticseries.nms:NMS_v1_19_4:$nmsVersion") {
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
            artifactId = "aquaticlib-21"
            version = "${project.version}"
            from(components["java"])
        }
    }
}
