package xyz.larkyy.aquaticseries.item.impl

import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import xyz.larkyy.aquaticseries.item.CustomItem

class OraxenItem(
    val id: String, name: String?, description: MutableList<String>?, amount: Int, modelData: Int,
    enchantments: MutableMap<Enchantment, Int>?, flags: MutableList<ItemFlag>?
) : CustomItem(name, description, amount, modelData, enchantments, flags) {
    override fun getUnmodifiedItem(): ItemStack {
        return OraxenItems.getItemById(id).build()
    }
}