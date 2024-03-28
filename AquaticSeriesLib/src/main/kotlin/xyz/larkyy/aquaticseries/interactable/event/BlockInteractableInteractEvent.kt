package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.event.player.PlayerInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable

class BlockInteractableInteractEvent(
    val originalEvent: PlayerInteractEvent,
    val blockInteractable: SpawnedBlockInteractable
) {
}