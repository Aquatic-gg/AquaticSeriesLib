package gg.aquatic.aquaticseries.lib.statistic

import java.util.concurrent.TimeUnit

abstract class Statistic<T: Any>(
    val id: String,
    val roundingMode: TimeUnit
) {

    abstract fun createPoint(value: T): StatisticPoint<T>
    abstract fun createEntry(total: T): StatisticEntry<T>

}