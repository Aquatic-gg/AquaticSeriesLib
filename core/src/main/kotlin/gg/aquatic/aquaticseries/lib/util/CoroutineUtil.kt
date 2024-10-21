package gg.aquatic.aquaticseries.lib.util

import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture

fun <T> Deferred<T>.thenAccept(consumer: (T) -> Unit) {
    asCompletableFuture().thenAccept(consumer)
}

fun <T> Deferred<T>.thenRun( runnable: () -> Unit) {
    asCompletableFuture().thenRun(runnable)
}

inline fun await(dispatcher: CoroutineDispatcher = Dispatchers.Default,crossinline runnable: suspend () -> Unit) = runBlocking {
    withContext(dispatcher) {
        launch {
            runnable()
        }
    }
}