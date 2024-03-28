package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.event.player.PlayerInteractEntityEvent
import xyz.larkyy.aquaticseries.interactable.impl.entity.SpawnedEntityInteractable

class EntityInteractableInteractEvent(
    val event: PlayerInteractEntityEvent,
    val entityInteractable: SpawnedEntityInteractable
) {

}