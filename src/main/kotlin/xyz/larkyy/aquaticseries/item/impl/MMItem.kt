package xyz.larkyy.aquaticseries.item.impl

import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.adapters.BukkitItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import xyz.larkyy.aquaticseries.item.CustomItem

class MMItem(
    val id: String, name: String?, description: MutableList<String>?, amount: Int, modelData: Int,
    enchantments: MutableMap<Enchantment, Int>?, flags: MutableList<ItemFlag>?

): CustomItem(name, description, amount, modelData, enchantments, flags) {
    override fun getUnmodifiedItem(): ItemStack {
        return (MythicProvider.get().itemManager.getItem(id).get()
            .generateItemStack(1) as BukkitItemStack).build()
    }
}