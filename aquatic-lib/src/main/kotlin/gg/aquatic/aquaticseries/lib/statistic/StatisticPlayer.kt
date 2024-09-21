package gg.aquatic.aquaticseries.lib.statistic

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import org.bukkit.entity.Player
import java.util.UUID

class StatisticPlayer(
    val uuid: UUID
) {

    val statistics = mutableMapOf<String, StatisticEntry<*>>()

    inline fun <reified T: Any> add(statistic: Statistic<T>, value: T) {
        val entry = statistics[statistic.id]
        if (entry == null) {
            statistics[statistic.id] = statistic.createEntry(value)
            return
        }
        if (entry.total !is T) {
            return
        }
        (entry as StatisticEntry<T>).addPoint(value)
    }

}

fun Player.getStatisticPlayer(): StatisticPlayer? {
    val feature = AbstractAquaticSeriesLib.INSTANCE.features[Features.STATISTIC] ?: return null
    return (feature as StatisticHandler).cache[this.uniqueId]
}