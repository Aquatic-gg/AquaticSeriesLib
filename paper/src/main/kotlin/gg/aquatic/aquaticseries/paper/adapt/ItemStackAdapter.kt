package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import gg.aquatic.aquaticseries.paper.PaperAdapter
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ItemStackAdapter: IItemStackAdapter {
    override fun displayName(string: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.displayName(PaperAdapter.minimessage.deserialize(string.string))
        itemStack.itemMeta = im
    }

    override fun displayName(string: AquaticString, itemMeta: ItemMeta) {
        itemMeta.displayName(PaperAdapter.minimessage.deserialize(string.string))
    }

    override fun lore(vararg strings: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.lore(strings.map { string -> PaperAdapter.minimessage.deserialize(string.string) })
        itemStack.itemMeta = im
    }

    override fun lore(strings: Collection<AquaticString>, itemStack: ItemStack) {
        lore(*strings.toTypedArray(), itemStack = itemStack)
    }

    override fun lore(vararg strings: AquaticString, itemMeta: ItemMeta) {
        itemMeta.lore(strings.map { string -> PaperAdapter.minimessage.deserialize(string.string) })
    }

    override fun lore(strings: Collection<AquaticString>, itemMeta: ItemMeta) {
        itemMeta.lore(strings.map { string -> PaperAdapter.minimessage.deserialize(string.string) })
    }
}