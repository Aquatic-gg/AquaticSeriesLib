package xyz.larkyy.aquaticseries.interactable

import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Consumer
import xyz.larkyy.aquaticseries.AquaticSeriesLib

abstract class AbstractInteractable {

    abstract val id: String
    abstract val serializer: AbstractInteractableSerializer<*>

    companion object {
        val INTERACTABLE_KEY =
            NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "Custom_Interactable")

        fun get(block: Block): AbstractInteractable? {
            val data = AbstractSpawnedInteractable.get(block) ?: return null
            return AquaticSeriesLib.INSTANCE.interactableHandler.registry[data.id]
        }
    }

    abstract fun spawn(location: Location): AbstractSpawnedInteractable

    abstract fun onChunkLoad(data: InteractableData, location: Location)
    abstract fun onChunkUnload(data: InteractableData)
}