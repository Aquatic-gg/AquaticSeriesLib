package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.item.CustomItem
import org.bukkit.Material

fun Material.toCustomItem(): CustomItem {
    return CustomItem.create("$this",null,null,1,-1,null,null)
}