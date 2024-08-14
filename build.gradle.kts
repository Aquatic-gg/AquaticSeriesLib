import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
}

val projectVersion = "1.0.1.4"

group = "gg.aquatic.aquaticseries"
version = projectVersion

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://repo.oraxen.com/releases")
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api("io.lumine:Mythic-Dist:5.3.5")
    implementation("com.jeff-media:custom-block-data:2.2.2")
    compileOnly("io.th0rgal:oraxen:1.171.0")
    compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.2-beta-r3-b")
    compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.4")
    //implementation("net.kyori:adventure-api:4.17.0")
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

extra["maven_username"] = env.fetch("MAVEN_USERNAME")
extra["maven_password"] = env.fetch("MAVEN_PASSWORD")

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")

    version = projectVersion

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://mvn.lumine.io/repository/maven-public/")

        maven("https://repo.papermc.io/repository/maven-public/")

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://mvn.lumine.io/repository/maven-public/")
        maven("https://mvn.lumine.io/repository/maven-public/")
        maven("https://repo.oraxen.com/releases")
        maven("https://jitpack.io")
    }

    dependencies {
        //compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        api("io.lumine:Mythic-Dist:5.3.5")
        implementation("com.jeff-media:custom-block-data:2.2.2")
        compileOnly("io.th0rgal:oraxen:1.171.0")
        compileOnly("com.github.LoneDev6:API-ItemsAdder:3.6.2-beta-r3-b")
        compileOnly ("com.ticxo.modelengine:ModelEngine:R4.0.4")
        //implementation("net.kyori:adventure-api:4.17.0")
    }

    kotlin {
        jvmToolchain(17)
    }
}