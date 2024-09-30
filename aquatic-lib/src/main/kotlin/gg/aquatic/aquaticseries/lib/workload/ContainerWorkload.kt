package gg.aquatic.aquaticseries.lib.workload

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.Bukkit
import java.util.concurrent.CompletableFuture

class ContainerWorkload(
    val workloads: MutableList<AbstractWorkload>,
    val delay: Long

) : AbstractWorkload() {
    override var isRunning: Boolean = false
    var future: CompletableFuture<Void> = CompletableFuture()

    override fun runNext() {
        if (!isRunning) return
        if (workloads.isEmpty()) {
            isRunning = false
            future.complete(null)
            return
        }

        val job = workloads.removeAt(0)
        job.run().thenRun {
            Bukkit.getScheduler().runTaskLater(
                AquaticSeriesLib.INSTANCE.plugin,
                Runnable {
                    runNext()
                },
                delay
            )
        }
    }

    override fun run(): CompletableFuture<Void> {
        if (isRunning) return future
        isRunning = true
        future = CompletableFuture()
        runNext()
        return future
    }

    override fun cancelAll() {
        for (workload in workloads) {
            workload.cancelAll()
        }
        workloads.clear()
        isRunning = false
        future = CompletableFuture()
    }
}