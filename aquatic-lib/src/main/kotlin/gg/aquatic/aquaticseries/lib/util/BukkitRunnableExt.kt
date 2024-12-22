package gg.aquatic.aquaticseries.lib.util

import com.tcoded.folialib.wrapper.task.WrappedTask
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.Location
import org.bukkit.entity.Entity

inline fun runSync(crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runNextTick {
        runnable.invoke()
    }
}

inline fun runAsync(crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runAsync {
        runnable.invoke()
    }
}

inline fun runSyncTimer(delay: Long, period: Long, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runTimer(Runnable {
        runnable.invoke()
    }, delay, period)
}

inline fun runAsyncTimer(delay: Long, period: Long, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runTimer(Runnable {
        runnable.invoke()
    }, delay, period)
}

inline fun runLaterSync(delay: Long, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runLater(Runnable {
        runnable.invoke()
    }, delay)
}

inline fun runLaterAsync(delay: Long, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runLaterAsync(Runnable {
        runnable.invoke()
    }, delay)
}

inline fun runAtLocation(location: Location, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runAtLocation(location, { _: WrappedTask ->
        runnable.invoke()
    })
}

inline fun runAtEntity(entity: Entity, crossinline runnable: () -> Unit) {
    AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runAtEntity(entity, { _: WrappedTask ->
        runnable.invoke()
    })
}