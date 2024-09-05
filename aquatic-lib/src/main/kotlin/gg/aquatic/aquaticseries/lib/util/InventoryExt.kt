package gg.aquatic.aquaticseries.lib.util

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.addItem(dropIfFull: Boolean, vararg itemStack: ItemStack) {
    val map = this.addItem(*itemStack)
    if (!dropIfFull) return
    if (this.holder is Player) {
        val location = (this.holder as Player).location
        for (value in map.values) {
            location.world?.dropItem(location, value)
        }
    }

}