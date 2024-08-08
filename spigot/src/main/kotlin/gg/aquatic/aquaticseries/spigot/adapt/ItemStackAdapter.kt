package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import org.bukkit.inventory.ItemStack

object ItemStackAdapter: IItemStackAdapter {
    override fun displayName(string: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.setDisplayName(string.string)
        itemStack.itemMeta = im
    }

    override fun lore(vararg strings: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.lore = strings.map { string -> string.string }
        itemStack.itemMeta = im
    }

    override fun lore(strings: Collection<AquaticString>, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.lore = strings.map { string -> string.string }
        itemStack.itemMeta = im
    }

}