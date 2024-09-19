package gg.aquatic.aquaticseries.lib.statistic.type.doublestat

import gg.aquatic.aquaticseries.lib.statistic.StatisticEntry
import java.util.concurrent.TimeUnit

class DoubleStatisticEntry(statistic: DoubleStatistic, total: Double, roundingMode: TimeUnit) : StatisticEntry<Double>(statistic, total,
    roundingMode
) {

    override fun addToTotal(value: Double) {
        total += value
    }

    override fun addPoint(value: Double) {
        points.add(statistic.createPoint(value))
    }
}