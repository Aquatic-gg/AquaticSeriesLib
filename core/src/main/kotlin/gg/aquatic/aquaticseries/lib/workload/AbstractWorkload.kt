package gg.aquatic.aquaticseries.lib.workload

import java.util.concurrent.CompletableFuture

abstract class AbstractWorkload {

    abstract var isRunning: Boolean

    abstract fun runNext()
    abstract fun run(): CompletableFuture<Void>
    abstract fun cancelAll()

}