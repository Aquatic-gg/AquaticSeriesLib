package gg.aquatic.aquaticseries.lib

import com.tcoded.folialib.FoliaLib
import org.bukkit.plugin.java.JavaPlugin

abstract class AbstractAquaticSeriesLib {

    abstract val plugin: JavaPlugin

    abstract fun getFoliaLib(): FoliaLib

    companion object {
        lateinit var instance: AbstractAquaticSeriesLib
    }
}