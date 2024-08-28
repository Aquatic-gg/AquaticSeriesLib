package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.entity.Player

abstract class AbstractSpawnedPacketInteractable<T : AbstractInteractable<*>> : SpawnedInteractable<T> {

    abstract val audience: AudienceList
    abstract fun show(player: Player)
    abstract fun hide(player: Player)

    protected fun handleAudienceShow(player: Player): Boolean {
        if (audience.mode == AudienceList.Mode.WHITELIST) {
            if (audience.whitelist.contains(player.uniqueId)) return true
            audience.whitelist += player.uniqueId

        } else if (audience.mode == AudienceList.Mode.BLACKLIST) {
            if (!audience.whitelist.contains(player.uniqueId)) return true
            audience.whitelist -= player.uniqueId
        }
        return false
    }

    protected fun handleAudienceHide(player: Player): Boolean {
        if (audience.mode == AudienceList.Mode.WHITELIST) {
            if (!audience.whitelist.contains(player.uniqueId)) return true
            audience.whitelist -= player.uniqueId

        } else if (audience.mode == AudienceList.Mode.BLACKLIST) {
            if (audience.whitelist.contains(player.uniqueId)) return true
            audience.whitelist += player.uniqueId
        }
        return false
    }
}