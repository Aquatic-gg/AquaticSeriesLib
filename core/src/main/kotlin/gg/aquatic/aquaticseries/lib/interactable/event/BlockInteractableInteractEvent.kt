package gg.aquatic.aquaticseries.lib.interactable.event

import org.bukkit.event.player.PlayerInteractEvent
import gg.aquatic.aquaticseries.interactable.impl.block.SpawnedBlockInteractable

class BlockInteractableInteractEvent(
    val originalEvent: PlayerInteractEvent,
    val blockInteractable: SpawnedBlockInteractable
) {



}