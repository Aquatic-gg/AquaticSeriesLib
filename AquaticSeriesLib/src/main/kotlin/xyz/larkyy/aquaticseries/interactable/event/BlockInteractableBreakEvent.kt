package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.event.block.BlockBreakEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable

class BlockInteractableBreakEvent(
    val originalEvent: BlockBreakEvent,
    val blockInteractable: SpawnedBlockInteractable
) {
}