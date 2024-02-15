package xyz.larkyy.bestiary.item.impl

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import xyz.larkyy.bestiary.item.CustomItem

class VanillaItem(
    val material: Material,
    name: String?,
    description: MutableList<String>?,
    amount: Int,
    modelData: Int,
    enchantments: MutableMap<Enchantment, Int>?,
    flags: MutableList<ItemFlag>?,
    ): CustomItem(
    name,
    description,
    amount,
    modelData,
    enchantments,
    flags
) {
    override fun getUnmodifiedItem(): ItemStack {
        return ItemStack(material)
    }
}