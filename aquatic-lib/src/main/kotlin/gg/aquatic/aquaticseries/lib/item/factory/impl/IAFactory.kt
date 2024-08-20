package gg.aquatic.aquaticseries.lib.item.factory.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.item.factory.ItemFactory
import gg.aquatic.aquaticseries.lib.item.impl.IAItem
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag

object IAFactory: ItemFactory {
    override fun create(
        id: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modelData: Int,
        enchantments: MutableMap<Enchantment, Int>?,
        flags: MutableList<ItemFlag>?,
        spawnerEntityType: EntityType?
    ): CustomItem {
        return IAItem(id, name, description, amount, modelData, enchantments, flags,spawnerEntityType)
    }
}