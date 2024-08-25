package gg.aquatic.aquaticseries.lib.interactable2

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action

class InteractableInteractEvent(
    val player: Player,
    val action: Action,
    val location: Location?,
    val interactable: SpawnedInteractable<*>
) {

    var cancelled = false

}