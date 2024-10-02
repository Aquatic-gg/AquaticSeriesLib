package gg.aquatic.aquaticseries.lib.item.impl

import dev.lone.itemsadder.api.CustomStack
import gg.aquatic.aquaticseries.lib.item.CustomItem
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class IAItem(val id: String,
             override val name: String?,
             override val description: MutableList<String>?,
             override val amount: Int,
             override val modelData: Int,
             override val enchantments: MutableMap<String, Int>?,
             override val flags: MutableList<ItemFlag>?, override val spawnerEntityType: EntityType?
): CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return CustomStack.getInstance(id)!!.itemStack
    }
}