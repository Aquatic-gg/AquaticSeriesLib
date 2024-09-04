package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import org.bukkit.inventory.ItemStack

class Section<T>(
    val name: String,
    val item: ItemStack,
): Edit<T> {
}