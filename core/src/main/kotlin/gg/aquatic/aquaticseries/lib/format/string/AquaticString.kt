package gg.aquatic.aquaticseries.lib.format.string

import gg.aquatic.aquaticcrates.api.AbstractAudience
import org.bukkit.entity.Player

interface AquaticString {

    fun send(player: Player)
    fun send(audience: AbstractAudience)
    fun broadcast()
    fun send(vararg players: Player)

}