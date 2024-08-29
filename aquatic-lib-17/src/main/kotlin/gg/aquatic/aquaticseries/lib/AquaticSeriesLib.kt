package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.displayentity.IDisplayEntityAdapter
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.nms.v1_17_1.NMS_1_17_1
import gg.aquatic.aquaticseries.nms.v1_18_2.NMS_1_18_2
import gg.aquatic.aquaticseries.nms.v1_19_4.NMS_1_19_4
import gg.aquatic.aquaticseries.nms.v1_20_1.NMS_1_20_1
import gg.aquatic.aquaticseries.nms.v1_20_4.NMS_1_20_4
import gg.aquatic.aquaticseries.nms.v1_20_6.menu.NMS_1_20_6
import gg.aquatic.aquaticseries.nms.v1_21.NMS_1_21
import gg.aquatic.aquaticseries.nms.v1_21_1.NMS_1_21_1
import org.bukkit.plugin.java.JavaPlugin

class AquaticSeriesLib private constructor(
    plugin: JavaPlugin,
    nmsAdapter: NMSAdapter?,
    features: HashMap<Features, IFeature>
) : AbstractAquaticSeriesLib(plugin, nmsAdapter, features) {

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

        /*
        private fun chooseDisplayAdapter(plugin: JavaPlugin): IDisplayEntityAdapter? {
            when (plugin.server.bukkitVersion) {
                "1.17.1-R0.1-SNAPSHOT", "1.18.2-R0.1-SNAPSHOT" -> {
                    return null
                }

                "1.19.4-R0.1-SNAPSHOT" -> {
                    return DisplayEntityAdapter
                }

                "1.20.4-R0.1-SNAPSHOT", "1.20.5-R0.1-SNAPSHOT", "1.20.6-R0.1-SNAPSHOT", "1.21-R0.1-SNAPSHOT", "1.21.1-R0.1-SNAPSHOT" -> {
                    return DisplayEntityAdapter
                }
            }
            return null
        }

         */

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

}