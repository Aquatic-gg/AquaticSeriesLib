package gg.aquatic.aquaticseries.lib

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.displayName(string: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.displayName(string, this)
}
fun ItemStack.lore(vararg strings: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemStack.lore(strings: List<AquaticString>) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}

fun ItemMeta.displayName(string: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.displayName(string, this)
}
fun ItemMeta.lore(vararg strings: AquaticString) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}
fun ItemMeta.lore(strings: List<AquaticString>) {
    AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.lore(strings = strings, this)
}