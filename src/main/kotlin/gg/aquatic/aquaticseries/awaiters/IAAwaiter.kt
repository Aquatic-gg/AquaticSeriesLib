package gg.aquatic.aquaticseries.awaiters

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import java.util.concurrent.CompletableFuture

class IAAwaiter(val lib: AquaticSeriesLib): AbstractAwaiter() {
    override val future: CompletableFuture<Void> = CompletableFuture()

    init {
        lib.plugin.server.pluginManager.registerEvents(Listeners(),lib.plugin)
    }

    inner class Listeners: Listener {
        @EventHandler
        fun onIALoad(event: ItemsAdderLoadDataEvent) {
            future.complete(null)
            loaded = true
        }
    }
}