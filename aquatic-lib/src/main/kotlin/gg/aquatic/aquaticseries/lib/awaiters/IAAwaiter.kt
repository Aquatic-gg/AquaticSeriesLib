package gg.aquatic.aquaticseries.lib.awaiters

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.register
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.concurrent.CompletableFuture

class IAAwaiter(val lib: AbstractAquaticSeriesLib): AbstractAwaiter() {
    override val future: CompletableFuture<Void> = CompletableFuture()

    init {
        Listeners().register()
    }

    inner class Listeners: Listener {
        @EventHandler
        fun onIALoad(event: ItemsAdderLoadDataEvent) {
            future.complete(null)
            loaded = true
        }
    }
}