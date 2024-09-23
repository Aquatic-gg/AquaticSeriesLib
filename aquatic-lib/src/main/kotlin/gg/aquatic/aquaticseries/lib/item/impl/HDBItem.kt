package gg.aquatic.aquaticseries.lib.item.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class HDBItem(val id: String,
              override val name: String?,
              override val description: MutableList<String>?,
              override val amount: Int,
              override val modelData: Int,
              override val enchantments: MutableMap<Enchantment, Int>?,
              override val flags: MutableList<ItemFlag>?, override val spawnerEntityType: EntityType?
): CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return HeadDatabaseAPI().getItemHead(id)
    }
}