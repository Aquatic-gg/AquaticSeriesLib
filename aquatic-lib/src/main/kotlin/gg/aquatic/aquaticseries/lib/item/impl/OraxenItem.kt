package gg.aquatic.aquaticseries.lib.item.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class OraxenItem(
    val id: String,
    override val name: String?,
    override val description: MutableList<String>?,
    override val amount: Int,
    override val modelData: Int,
    override val enchantments: MutableMap<String, Int>?,
    override val flags: MutableList<ItemFlag>?, override val spawnerEntityType: EntityType?
) : CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return OraxenItems.getItemById(id).build()
    }
}