package gg.aquatic.aquaticseries.lib.interactable

import org.bukkit.entity.Player
import java.util.UUID

abstract class AbstractSpawnedPacketInteractable: AbstractSpawnedInteractable() {

    val currentlyViewing = mutableListOf<UUID>()

    abstract fun show(player: Player)
    abstract fun hide(player: Player)

}