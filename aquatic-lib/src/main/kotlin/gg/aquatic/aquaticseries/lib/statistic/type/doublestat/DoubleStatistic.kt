package gg.aquatic.aquaticseries.lib.statistic.type.doublestat

import gg.aquatic.aquaticseries.lib.statistic.Statistic
import gg.aquatic.aquaticseries.lib.statistic.StatisticEntry
import gg.aquatic.aquaticseries.lib.statistic.StatisticPoint
import java.util.concurrent.TimeUnit

class DoubleStatistic(id: String, roundingMode: TimeUnit) : Statistic<Double>(id, roundingMode) {
    override fun createPoint(value: Double): StatisticPoint<Double> {
        return StatisticPoint(System.currentTimeMillis(), value)
    }

    override fun createEntry(total: Double): StatisticEntry<Double> {
        return DoubleStatisticEntry(this, total, roundingMode)
    }
}