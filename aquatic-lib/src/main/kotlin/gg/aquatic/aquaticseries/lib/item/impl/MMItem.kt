package gg.aquatic.aquaticseries.lib.item.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.adapters.BukkitItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class MMItem(
    val id: String,
    override val name: String?,
    override val description: MutableList<String>?,
    override val amount: Int,
    override val modelData: Int,
    override val enchantments: MutableMap<String, Int>?,
    override val flags: MutableList<ItemFlag>?, override val spawnerEntityType: EntityType?

): CustomItem() {
    override fun getUnmodifiedItem(): ItemStack {
        return (MythicProvider.get().itemManager.getItem(id).get()
            .generateItemStack(1) as BukkitItemStack).build()
    }
}