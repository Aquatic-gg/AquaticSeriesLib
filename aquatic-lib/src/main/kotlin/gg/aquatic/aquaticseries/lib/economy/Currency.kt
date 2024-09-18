package gg.aquatic.aquaticseries.lib.economy

import org.bukkit.entity.Player

interface Currency {

    val id: String

    fun give(player: Player, amount: Double)
    fun take(player: Player, amount: Double)
    fun set(player: Player, amount: Double)
    fun getBalance(player: Player): Double
    fun has(player: Player, amount: Double): Boolean

}