package gg.aquatic.aquaticseries.lib.item.factory.impl

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import gg.aquatic.aquaticseries.item.CustomItem
import gg.aquatic.aquaticseries.item.factory.ItemFactory
import gg.aquatic.aquaticseries.item.impl.MMItem

object MMFactory: ItemFactory {
    override fun create(
        id: String,
        name: String?,
        description: MutableList<String>?,
        amount: Int,
        modelData: Int,
        enchantments: MutableMap<Enchantment, Int>?,
        flags: MutableList<ItemFlag>?
    ): CustomItem {
        return MMItem(id, name, description, amount, modelData, enchantments, flags)
    }
}