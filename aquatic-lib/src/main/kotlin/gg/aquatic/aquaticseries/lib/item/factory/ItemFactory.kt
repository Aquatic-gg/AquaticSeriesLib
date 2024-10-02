package gg.aquatic.aquaticseries.lib.item.factory

import gg.aquatic.aquaticseries.lib.item.CustomItem
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag

interface ItemFactory {

    fun create(id: String, name: String?, description: MutableList<String>?, amount: Int, modelData: Int,
               enchantments: MutableMap<String,Int>?, flags: MutableList<ItemFlag>?, spawnerEntityType: EntityType?): CustomItem
}