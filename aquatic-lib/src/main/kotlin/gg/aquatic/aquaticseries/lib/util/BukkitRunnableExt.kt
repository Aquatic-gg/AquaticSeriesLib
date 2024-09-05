package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import org.bukkit.scheduler.BukkitRunnable

inline fun runSync(crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTask(AbstractAquaticSeriesLib.INSTANCE.plugin)
}

inline fun runAsync(crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskAsynchronously(AbstractAquaticSeriesLib.INSTANCE.plugin)
}

inline fun runSyncTimer(delay: Long, period: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskTimer(AbstractAquaticSeriesLib.INSTANCE.plugin, delay, period)
}
inline fun runAsyncTimer(delay: Long, period: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskTimerAsynchronously(AbstractAquaticSeriesLib.INSTANCE.plugin, delay, period)
}

inline fun runLaterSync(delay: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin, delay)
}

inline fun runLaterAsync(delay: Long, crossinline runnable: BukkitRunnable.() -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable.invoke(this)
        }
    }.runTaskLaterAsynchronously(AbstractAquaticSeriesLib.INSTANCE.plugin, delay)
}