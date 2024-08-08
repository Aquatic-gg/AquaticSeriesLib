package gg.aquatic.aquaticseries.interactable.impl.block

import org.bukkit.Location
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.InteractableData

class BlockInteractableSerializer : AbstractInteractableSerializer<SpawnedBlockInteractable>() {
    override fun serialize(value: SpawnedBlockInteractable): String {
        return ""
    }

    override fun deserialize(
        value: InteractableData,
        location: Location,
        interactable: AbstractInteractable
    ): SpawnedBlockInteractable? {
        if (interactable !is BlockInteractable) return null
        interactable.despawnOldData(value, location)
        return interactable.spawn(location)
    }
}