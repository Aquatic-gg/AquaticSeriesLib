package gg.aquatic.aquaticseries.lib.interactable2

import org.bukkit.entity.Player

abstract class AbstractSpawnedPacketInteractable<T : AbstractInteractable<*>> : SpawnedInteractable<T> {

    abstract val audience: AudienceList
    abstract fun show(player: Player)
    abstract fun hide(player: Player)

}