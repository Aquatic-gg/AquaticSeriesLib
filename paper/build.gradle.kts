group = "gg.aquatic.aquaticseries.paper"
version = "1.0"

repositories {
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.17.0")
    api(project(":core"))
}