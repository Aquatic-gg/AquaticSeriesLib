package xyz.larkyy.aquaticseries

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

abstract class AbstractAquaticModuleInject(
    val plugin: JavaPlugin, val dataFolder: File
) {

    abstract fun inject()
}