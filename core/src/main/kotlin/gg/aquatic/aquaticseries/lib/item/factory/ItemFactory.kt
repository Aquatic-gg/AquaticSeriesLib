package gg.aquatic.aquaticseries.lib.item.factory

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import gg.aquatic.aquaticseries.item.CustomItem

interface ItemFactory {

    fun create(id: String, name: String?, description: MutableList<String>?, amount: Int, modelData: Int,
               enchantments: MutableMap<Enchantment,Int>?, flags: MutableList<ItemFlag>?): CustomItem
}