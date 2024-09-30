package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.scheduler.BukkitRunnable

inline fun runSync(crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTask(AquaticSeriesLib.INSTANCE.plugin)
}

inline fun runAsync(crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskAsynchronously(AquaticSeriesLib.INSTANCE.plugin)
}

inline fun runSyncTimer(delay: Long, period: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskTimer(AquaticSeriesLib.INSTANCE.plugin, delay, period)
}
inline fun runAsyncTimer(delay: Long, period: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskTimerAsynchronously(AquaticSeriesLib.INSTANCE.plugin, delay, period)
}

inline fun runLaterSync(delay: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskLater(AquaticSeriesLib.INSTANCE.plugin, delay)
}

inline fun runLaterAsync(delay: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskLaterAsynchronously(AquaticSeriesLib.INSTANCE.plugin, delay)
}