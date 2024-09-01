package gg.aquatic.aquaticseries.lib.interactable2

import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.BlacklistAudience
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import org.bukkit.entity.Player

abstract class AbstractSpawnedPacketInteractable<T : AbstractInteractable<*>> : SpawnedInteractable<T> {

    var canInteract: Boolean = true
    abstract val audience: AquaticAudience
    abstract fun show(player: Player)
    abstract fun hide(player: Player)

    protected fun handleAudienceShow(player: Player): Boolean {
        val au = audience
        if (au is WhitelistAudience) {
            if (au.contains(player)) return true
            au.add(player)
        } else if (au is BlacklistAudience) {
            if (!au.contains(player)) return true
            au.remove(player)
        }
        return false
    }

    protected fun handleAudienceHide(player: Player): Boolean {
        val au = audience
        if (au is WhitelistAudience) {
            if (!au.contains(player)) return true
            au.remove(player)
        } else if (au is BlacklistAudience) {
            if (au.contains(player)) return true
            au.add(player)
        }
        return false
    }
}