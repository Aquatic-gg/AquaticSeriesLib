package gg.aquatic.aquaticseries.interactable.event

import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.meg.SpawnedMegInteractable

class MegInteractableInteractEvent(
    val originalEvent: BaseEntityInteractEvent,
    val interactable: SpawnedMegInteractable,
) {
}