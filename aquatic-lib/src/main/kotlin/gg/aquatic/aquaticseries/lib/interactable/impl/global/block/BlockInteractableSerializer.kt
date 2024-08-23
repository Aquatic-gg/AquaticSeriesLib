package gg.aquatic.aquaticseries.lib.interactable.impl.global.block

import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable
import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractableSerializer
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import org.bukkit.Location

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