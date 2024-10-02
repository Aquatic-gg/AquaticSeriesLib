package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.item2.AquaticItem
import gg.aquatic.aquaticseries.lib.item2.ItemHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.toCustomItem(): AquaticItem {
    return ItemHandler.create(ItemStack(this))
}