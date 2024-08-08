package gg.aquatic.aquaticseries.lib.interactable.impl.block

import org.bukkit.Location
import gg.aquatic.aquaticseries.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.interactable.AbstractInteractableSerializer
import gg.aquatic.aquaticseries.interactable.InteractableData

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