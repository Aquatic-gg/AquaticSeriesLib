package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

interface IItemStackAdapter{

    fun displayName(string: AquaticString, itemStack: ItemStack)
    fun lore(vararg strings: AquaticString, itemStack: ItemStack)
    fun lore(strings: Collection<AquaticString>, itemStack: ItemStack)

    fun displayName(string: AquaticString, itemMeta: ItemMeta)
    fun lore(vararg strings: AquaticString, itemMeta: ItemMeta)
    fun lore(strings: Collection<AquaticString>, itemMeta: ItemMeta)

    fun getAquaticLore(itemStack: ItemStack): List<AquaticString>
    fun getAquaticDisplayName(itemStack: ItemStack): AquaticString?

    fun getAquaticLore(itemMeta: ItemMeta): List<AquaticString>
    fun getAquaticDisplayName(itemMeta: ItemMeta): AquaticString?

}