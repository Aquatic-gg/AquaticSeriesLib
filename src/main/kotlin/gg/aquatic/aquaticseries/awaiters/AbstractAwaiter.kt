package gg.aquatic.aquaticseries.awaiters

import java.util.concurrent.CompletableFuture

abstract class AbstractAwaiter {
    abstract val future: CompletableFuture<Void>
    var loaded = false

}