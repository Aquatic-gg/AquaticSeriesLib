package gg.aquatic.aquaticseries.lib.workload

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import java.util.concurrent.CompletableFuture

class Workload(
    val jobs: MutableList<Runnable>,
    val delay: Long
): AbstractWorkload() {

    override var isRunning: Boolean = false
    private var future: CompletableFuture<Void> = CompletableFuture()

    override fun run(): CompletableFuture<Void> {
        if (isRunning) return future
        isRunning = true
        future = CompletableFuture()
        runNext()
        return future
    }

    override fun runNext() {
        if (!isRunning) return
        if (jobs.isEmpty()) {
            isRunning = false
            future.complete(null)
            return
        }

        val job = jobs.removeAt(0)
        job.run()
        AquaticSeriesLib.INSTANCE.getFoliaLib().scheduler.runLater(Runnable {
            runNext()
        }, delay)
    }

    override fun cancelAll() {
        jobs.clear()
        isRunning = false
        future = CompletableFuture()
    }
}