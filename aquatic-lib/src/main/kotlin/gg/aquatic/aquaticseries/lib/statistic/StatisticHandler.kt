package gg.aquatic.aquaticseries.lib.statistic

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import java.util.*

class StatisticHandler: IFeature {


    val cache = HashMap<UUID, StatisticPlayer>()
    override val type: Features = Features.STATISTIC

    override fun initialize(lib: AquaticSeriesLib) {

    }

}