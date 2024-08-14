package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
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
            _INSTANCE = AquaticSeriesLib(plugin, null, HashMap(features.associateBy { it.type }))
            return _INSTANCE!!
        }
    }

}