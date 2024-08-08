package gg.aquatic.aquaticseries.lib.interactable.event

import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.meg.SpawnedMegInteractable

class MegInteractableInteractEvent(
    val originalEvent: BaseEntityInteractEvent,
    val interactable: SpawnedMegInteractable,
) {
}