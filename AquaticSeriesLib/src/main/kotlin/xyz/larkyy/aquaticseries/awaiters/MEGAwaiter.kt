package xyz.larkyy.aquaticseries.awaiters

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.events.ModelRegistrationEvent
import com.ticxo.modelengine.api.generator.ModelGenerator
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import java.util.concurrent.CompletableFuture

class MEGAwaiter(val lib: AquaticSeriesLib): AbstractAwaiter() {
    override val future: CompletableFuture<Void> = CompletableFuture()

    init {
        if (ModelEngineAPI.getAPI().modelGenerator.isInitialized) {
            future.complete(null)
            loaded = true
        } else {
            lib.plugin.server.pluginManager.registerEvents(Listeners(),lib.plugin)
        }
    }

    inner class Listeners: Listener {
        @EventHandler
        fun onMegInit(event: ModelRegistrationEvent) {
            if (event.phase == ModelGenerator.Phase.FINISHED) {
                future.complete(null)
                loaded = true
            }
        }
    }

}