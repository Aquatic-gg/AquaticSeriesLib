package xyz.larkyy.aquaticseries.interactable.event

import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import xyz.larkyy.aquaticseries.interactable.impl.entity.SpawnedEntityInteractable

class EntityInteractableDamageEvent(
    val originalEvent: EntityDamageByEntityEvent,
    val entityInteractable: SpawnedEntityInteractable
) {



}