package gg.aquatic.aquaticseries.lib.awaiters

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.concurrent.CompletableFuture

class IAAwaiter(val lib: AbstractAquaticSeriesLib): AbstractAwaiter() {
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