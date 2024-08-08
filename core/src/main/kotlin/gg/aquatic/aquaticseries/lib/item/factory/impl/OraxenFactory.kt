package gg.aquatic.aquaticseries.lib.item.factory.impl

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import aquaticseries.item.impl.OraxenItem
import gg.aquatic.aquaticseries.item.CustomItem
import gg.aquatic.aquaticseries.item.factory.ItemFactory

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
        return aquaticseries.item.impl.OraxenItem(id, name, description, amount, modelData, enchantments, flags)
    }

}