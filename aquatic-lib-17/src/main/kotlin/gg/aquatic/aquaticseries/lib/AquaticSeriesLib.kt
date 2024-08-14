package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import gg.aquatic.aquaticseries.nms.NMS_1_17_1
import gg.aquatic.aquaticseries.nms.NMS_1_20_4
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

        private fun chooseNMSAdapter(plugin: JavaPlugin): NMSAdapter? {
            when (plugin.server.bukkitVersion) {
                "1.16.5-R0.1-SNAPSHOT" -> {

                }

                "1.17.1-R0.1-SNAPSHOT" -> {
                    return NMS_1_17_1()
                }

                "1.18.2-R0.1-SNAPSHOT" -> {

                }

                "1.19.2-R0.1-SNAPSHOT" -> {

                }

                "1.19.3-R0.1-SNAPSHOT" -> {

                }

                "1.19.4-R0.1-SNAPSHOT" -> {

                }

                "1.20.1-R0.1-SNAPSHOT", "1.20-R0.1-SNAPSHOT" -> {

                }

                "1.20.2-R0.1-SNAPSHOT" -> {

                }

                "1.20.4-R0.1-SNAPSHOT" -> {
                    return NMS_1_20_4()
                }

                "1.20.5-R0.1-SNAPSHOT" -> {

                }

                "1.21-R0.1-SNAPSHOT" -> {

                }
            }
            return null
        }
    }

}