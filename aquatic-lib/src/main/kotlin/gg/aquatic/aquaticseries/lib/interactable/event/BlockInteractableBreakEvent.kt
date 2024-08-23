package gg.aquatic.aquaticseries.lib.interactable.event

import org.bukkit.event.block.BlockBreakEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.global.block.SpawnedBlockInteractable

class BlockInteractableBreakEvent(
    val originalEvent: BlockBreakEvent,
    val blockInteractable: SpawnedBlockInteractable
) {
}