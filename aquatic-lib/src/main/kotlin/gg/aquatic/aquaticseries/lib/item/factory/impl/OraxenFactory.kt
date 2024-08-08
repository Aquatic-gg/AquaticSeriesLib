package gg.aquatic.aquaticseries.lib.item.factory.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.item.factory.ItemFactory
import gg.aquatic.aquaticseries.lib.item.impl.OraxenItem
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag

object OraxenFactory: ItemFactory {

    override fun create(
        id: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modelData: Int,
        enchantments: MutableMap<Enchantment, Int>?,
        flags: MutableList<ItemFlag>?
    ): CustomItem {
        return OraxenItem(id, name, description, amount, modelData, enchantments, flags)
    }

}