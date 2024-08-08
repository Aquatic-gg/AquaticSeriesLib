package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.inventory.ItemStack

interface IItemStackAdapter{

    fun displayName(string: AquaticString, itemStack: ItemStack)
    fun lore(vararg strings: AquaticString, itemStack: ItemStack)
    fun lore(strings: Collection<AquaticString>, itemStack: ItemStack)

}