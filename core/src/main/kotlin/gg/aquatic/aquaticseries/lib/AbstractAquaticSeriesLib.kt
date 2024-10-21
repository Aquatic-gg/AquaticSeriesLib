package gg.aquatic.aquaticseries.lib

import org.bukkit.plugin.java.JavaPlugin

abstract class AbstractAquaticSeriesLib {

    abstract val plugin: JavaPlugin

    companion object {
        lateinit var instance: AbstractAquaticSeriesLib
    }
}