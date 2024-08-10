package gg.aquatic.aquaticseries.lib

import com.google.gson.Gson
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.format.Format
import gg.aquatic.aquaticseries.lib.interactable.InteractableHandler
import gg.aquatic.aquaticseries.paper.PaperAdapter
import gg.aquatic.aquaticseries.spigot.SpigotAdapter
import org.bukkit.plugin.java.JavaPlugin

class AquaticSeriesLib private constructor(val plugin: JavaPlugin, val features: HashMap<Features,IFeature>) {

    val interactableHandler: InteractableHandler?
        get() {
            return features[Features.INTERACTABLES] as? InteractableHandler?
        }

    var adapter: AquaticLibAdapter
    var isPaper = false
    private var messageFormat: Format

    init {

        try {
            // Any other works, just the shortest I could find.
            Class.forName("com.destroystokyo.paper.ParticleBuilder")
            isPaper = true
        } catch (ignored: ClassNotFoundException) {
        }

        adapter = if (isPaper) {
            messageFormat = Format.MINIMESSAGE
            PaperAdapter(plugin)
        } else {
            messageFormat = Format.LEGACY
            SpigotAdapter(plugin)
        }
        println("[AquaticSeriesLib] Currently using $messageFormat message formatting!")
    }

    fun getMessageFormatting(): Format {
        return messageFormat
    }

    fun setMessageFormatting(format: Format) {
        if (!isPaper && format == Format.MINIMESSAGE) {
            return
        }
        messageFormat = format

        adapter = if (format == Format.MINIMESSAGE) {
            PaperAdapter(plugin)
        } else {
            SpigotAdapter(plugin)
        }
        println("[AquaticSeriesLib] Currently using $messageFormat message formatting!")
    }

    companion object {

        val GSON = Gson()
        private var _INSTANCE: AquaticSeriesLib? = null
        val INSTANCE: AquaticSeriesLib
            get() {
                if (_INSTANCE == null) {
                    throw Exception("Library was not initialized! Use the init() method first!")
                }
                return _INSTANCE!!
            }

        fun init(plugin: JavaPlugin, features: Collection<IFeature>): AquaticSeriesLib {
            val instance = _INSTANCE
            if (instance != null) return instance
            _INSTANCE = AquaticSeriesLib(plugin, HashMap(features.associateBy { it.type }))
            return _INSTANCE!!
        }
    }

}