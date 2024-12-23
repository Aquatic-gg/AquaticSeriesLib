package gg.aquatic.aquaticseries.lib.workload

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.Chunk
import java.util.concurrent.CompletableFuture

class ChunkWorkload(
    val chunk: Chunk,
    val jobs: MutableList<Runnable>,
    val delay: Long
): AbstractWorkload() {

    override var isRunning: Boolean = false
    private var future: CompletableFuture<Void> = CompletableFuture()

    init {
        val list = ChunkWorkloadHandler.INSTANCE.workloads.getOrPut(chunk) {ArrayList()}
        list += this
    }

    override fun runNext() {
        if (!isRunning) return
        if (jobs.isEmpty()) {
            future.complete(null)
            isRunning = false
            return
        }
        val job = jobs.removeAt(0)

        job.run()
        AquaticSeriesLib.INSTANCE.foliaLib.scheduler.runLater(Runnable {
            runNext()
        }, delay)
    }

    override fun run(): CompletableFuture<Void> {
        if (isRunning) return future
        future = CompletableFuture()
        isRunning = true
        runNext()
        return future
    }

    override fun cancelAll() {
        jobs.clear()
        isRunning = false
        future = CompletableFuture()
    }
}