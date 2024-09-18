package gg.aquatic.aquaticseries.lib.economy.impl

import gg.aquatic.aquaticseries.lib.economy.Currency
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemCurrency(
    val item: ItemStack, override val id: String
) : Currency {
    override fun give(player: Player, amount: Double) {
        for (i in 1..amount.toInt()) {
            player.inventory.addItem(item)
        }
    }

    override fun take(player: Player, amount: Double) {
        var removed = 0
        for (content in player.inventory.contents) {
            if (content.isSimilar(item)) {
                val toRemove = amount - removed
                if (content.amount < toRemove) {
                    removed += content.amount
                    content.amount = 0
                    continue
                }
                content.amount -= toRemove.toInt()
                return
            }
        }
    }

    override fun getBalance(player: Player): Double {
        var balance = 0
        for (content in player.inventory.contents) {
            if (content.isSimilar(item)) {
                balance += content.amount
            }
        }
        return balance.toDouble()
    }

    override fun has(player: Player, amount: Double): Boolean {
        return getBalance(player) >= amount
    }
}