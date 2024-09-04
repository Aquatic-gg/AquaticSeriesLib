package gg.aquatic.aquaticseries.lib.input

import org.bukkit.entity.Player

interface IHandler {

    fun isBeingAwaited(player: Player): Boolean

}