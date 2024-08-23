package gg.aquatic.aquaticseries.lib.interactable.event

import gg.aquatic.aquaticseries.lib.interactable.impl.global.block.SpawnedBlockInteractable
import org.bukkit.event.player.PlayerInteractEvent

class BlockInteractableInteractEvent(
    val originalEvent: PlayerInteractEvent,
    val blockInteractable: SpawnedBlockInteractable
) {



}