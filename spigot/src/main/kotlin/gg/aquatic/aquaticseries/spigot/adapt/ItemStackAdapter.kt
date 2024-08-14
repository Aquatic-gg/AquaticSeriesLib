package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ItemStackAdapter: IItemStackAdapter {
    override fun displayName(string: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.setDisplayName(if (string is SpigotString) string.formatted else string.string)
        itemStack.itemMeta = im
    }

    override fun displayName(string: AquaticString, itemMeta: ItemMeta) {
        itemMeta.setDisplayName(if (string is SpigotString) string.formatted else string.string)
    }

    override fun lore(vararg strings: AquaticString, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.lore = strings.map { string -> if (string is SpigotString) string.formatted else string.string }
        itemStack.itemMeta = im
    }

    override fun lore(strings: Collection<AquaticString>, itemStack: ItemStack) {
        val im = itemStack.itemMeta
        im?.lore = strings.map { string -> if (string is SpigotString) string.formatted else string.string }
        itemStack.itemMeta = im
    }

    override fun lore(vararg strings: AquaticString, itemMeta: ItemMeta) {
        itemMeta.lore = strings.map { string -> if (string is SpigotString) string.formatted else string.string }
    }

    override fun lore(strings: Collection<AquaticString>, itemMeta: ItemMeta) {
        itemMeta.lore = strings.map { string -> if (string is SpigotString) string.formatted else string.string }
    }

    override fun getAquaticLore(itemStack: ItemStack): List<AquaticString> {
        return itemStack.itemMeta?.lore?.map { SpigotString(it) } ?: emptyList()
    }

    override fun getAquaticDisplayName(itemStack: ItemStack): AquaticString? {
        return itemStack.itemMeta?.displayName?.let { SpigotString(it) }
    }

    override fun getAquaticLore(itemMeta: ItemMeta): List<AquaticString> {
        return itemMeta.lore?.map { SpigotString(it) } ?: emptyList()
    }

    override fun getAquaticDisplayName(itemMeta: ItemMeta): AquaticString {
        return SpigotString(itemMeta.displayName)
    }

}