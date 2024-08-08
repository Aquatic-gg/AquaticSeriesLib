package gg.aquatic.aquaticseries.lib.item.impl

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import gg.aquatic.aquaticseries.item.CustomItem

class VanillaItem(
    val material: Material,
    override val name: String?,
    override val description: MutableList<String>?,
    override val amount: Int,
    override val modelData: Int,
    override val enchantments: MutableMap<Enchantment, Int>?,
    override val flags: MutableList<ItemFlag>?,
    ): CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return ItemStack(material)
    }
}