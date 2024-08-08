package gg.aquatic.aquaticseries.lib.item.impl

import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import gg.aquatic.aquaticseries.item.CustomItem

class OraxenItem(
    val id: String,
    override val name: String?,
    override val description: MutableList<String>?,
    override val amount: Int,
    override val modelData: Int,
    override val enchantments: MutableMap<Enchantment, Int>?,
    override val flags: MutableList<ItemFlag>?
) : CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return OraxenItems.getItemById(id).build()
    }
}