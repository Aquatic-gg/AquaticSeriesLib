package gg.aquatic.aquaticseries.lib.statistic

import java.util.TreeMap
import java.util.concurrent.TimeUnit
import kotlin.math.round

abstract class StatisticEntry<T: Any>(
    val statistic: Statistic<T>,
    var total: T,
    val roundingMode: TimeUnit
) {
    val points: TreeMap<Long,StatisticPoint<T>> = TreeMap()

    abstract fun addToTotal(value: T)
    abstract fun addPoint(value: T)

    protected fun addPoint(timestamp: Long, value: T) {
        val actualTimeStamp = round(timestamp)
        if (points.containsKey(actualTimeStamp)) {
            points[actualTimeStamp]!!.value = value
        }
        else {
            points[actualTimeStamp] = statistic.createPoint(value)
        }
    }

    private fun round(timestamp: Long): Long {
        when(roundingMode) {
            TimeUnit.MILLISECONDS -> return timestamp
            TimeUnit.SECONDS -> {
                val seconds = (round(timestamp / 1000.0) * 1000L).toLong()
                return seconds
            }
            TimeUnit.MINUTES -> {
                val minutes = (round(timestamp / (1000.0*60.0)) * (1000L*60L)).toLong()
                return minutes
            }
            TimeUnit.HOURS -> {
                val hours = (round(timestamp / (1000.0*60.0*60.0)) * (1000L*60L*60L)).toLong()
                return hours
            }
            else ->{
                val days = (round(timestamp / (1000.0*60.0*60.0*24.0)) * (1000L*60L*60L*24.0)).toLong()
                return days
            }
        }
    }
}