package gg.aquatic.aquaticseries.item.factory.impl

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import xyz.larkyy.aquaticseries.item.CustomItem
import xyz.larkyy.aquaticseries.item.factory.ItemFactory
import xyz.larkyy.aquaticseries.item.impl.MMItem

class MMFactory: ItemFactory {
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