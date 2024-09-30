package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticLibAdapter
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.format.Format
import gg.aquatic.aquaticseries.lib.network.NetworkPacketListener
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.nms.v1_17_1.NMS_1_17_1
import gg.aquatic.aquaticseries.nms.v1_18_2.NMS_1_18_2
import gg.aquatic.aquaticseries.nms.v1_19_4.NMS_1_19_4
import gg.aquatic.aquaticseries.nms.v1_20_1.NMS_1_20_1
import gg.aquatic.aquaticseries.nms.v1_20_4.NMS_1_20_4
import gg.aquatic.aquaticseries.nms.v1_20_6.menu.NMS_1_20_6
import gg.aquatic.aquaticseries.nms.v1_21.NMS_1_21
import gg.aquatic.aquaticseries.nms.v1_21_1.NMS_1_21_1
import gg.aquatic.aquaticseries.paper.PaperAdapter
import gg.aquatic.aquaticseries.spigot.SpigotAdapter
import org.bukkit.plugin.java.JavaPlugin

class AquaticSeriesLib private constructor(
    val plugin: JavaPlugin,
    val nmsAdapter: NMSAdapter?,
    val features: HashMap<Features, IFeature>
) {

    val networkPacketListener: NetworkPacketListener?
        get() {
            return features[Features.NETWORKING] as? NetworkPacketListener?
        }

    var adapter: AquaticLibAdapter
    var isPaper = false
    private var messageFormat: Format

    companion object {
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
            val adapter = chooseNMSAdapter(plugin)
            _INSTANCE = AquaticSeriesLib(plugin, adapter, HashMap(features.associateBy { it.type }))
            return _INSTANCE!!
        }

        private fun chooseNMSAdapter(plugin: JavaPlugin): NMSAdapter? {
            when (plugin.server.bukkitVersion) {
                "1.17.1-R0.1-SNAPSHOT" -> {
                    return NMS_1_17_1()
                }

                "1.18.2-R0.1-SNAPSHOT" -> {
                    return NMS_1_18_2()
                }

                "1.19.4-R0.1-SNAPSHOT" -> {
                    return NMS_1_19_4()
                }

                "1.20.1-R0.1-SNAPSHOT" -> {
                    return NMS_1_20_1()
                }

                "1.20.4-R0.1-SNAPSHOT" -> {
                    return NMS_1_20_4()
                }

                "1.20.5-R0.1-SNAPSHOT", "1.20.6-R0.1-SNAPSHOT" -> {
                    return NMS_1_20_6()
                }

                "1.21-R0.1-SNAPSHOT" -> {
                    return NMS_1_21()
                }

                "1.21.1-R0.1-SNAPSHOT" -> {
                    return NMS_1_21_1()
                }
            }
            return null
        }
    }

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

        for (feature in features) {
            feature.value.initialize(this)
        }
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

}