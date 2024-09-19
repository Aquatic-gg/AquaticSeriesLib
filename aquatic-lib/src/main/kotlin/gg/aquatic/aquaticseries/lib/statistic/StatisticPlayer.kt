package gg.aquatic.aquaticseries.lib.statistic

class StatisticPlayer {

    val statistics = mutableMapOf<String, StatisticEntry<*>>()

    fun <T: Any> add(statistic: Statistic<T>, value: T) {
        val entry = statistics[statistic.id] ?: return
    }

}