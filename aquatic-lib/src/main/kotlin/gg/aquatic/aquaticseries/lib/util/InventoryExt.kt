package gg.aquatic.aquaticseries.lib.util

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.addOrDropItem(vararg itemStack: ItemStack) {
    val map = this.addItem(*itemStack)
    if (this.holder is Player) {
        val location = (this.holder as Player).location
        for (value in map.values) {
            location.world?.dropItem(location, value)
        }
    }

}