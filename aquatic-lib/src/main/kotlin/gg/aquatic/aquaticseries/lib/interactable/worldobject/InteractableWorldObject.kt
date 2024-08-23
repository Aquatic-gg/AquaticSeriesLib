package gg.aquatic.aquaticseries.lib.interactable.worldobject

import gg.aquatic.aquaticseries.lib.interactable.AbstractInteractable

interface InteractableWorldObject {

    val interactable: AbstractInteractable
    val packetBased: Boolean

}