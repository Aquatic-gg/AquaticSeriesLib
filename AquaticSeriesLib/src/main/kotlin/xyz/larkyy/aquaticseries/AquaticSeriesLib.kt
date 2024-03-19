package xyz.larkyy.aquaticseries

import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticseries.interactable.InteractableHandler

class AquaticSeriesLib private constructor(val plugin: JavaPlugin) {

    val interactableHandler = InteractableHandler()

    companion object {

        private var _INSTANCE: AquaticSeriesLib? = null
        val INSTANCE: AquaticSeriesLib
            get() {
                if (_INSTANCE == null) {
                    throw Exception("Library was not initialized! Use the init() method first!")
                }
                return _INSTANCE!!
            }

        fun init(plugin: JavaPlugin): AquaticSeriesLib {
            val instance = _INSTANCE
            if (instance != null) return instance
            _INSTANCE = AquaticSeriesLib(plugin)
            return _INSTANCE!!
        }
    }

}