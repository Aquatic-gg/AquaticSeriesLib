package gg.aquatic.aquaticseries.lib.interactable.impl.personalized

import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable.AudienceList
import org.bukkit.entity.Player
import java.util.UUID

abstract class AbstractSpawnedPacketInteractable: AbstractSpawnedInteractable() {

    var audienceList: AudienceList = AudienceList(mutableListOf(), AudienceList.Mode.WHITELIST)

    abstract fun show(player: Player)
    abstract fun hide(player: Player)

    abstract fun updateVisibility()

}