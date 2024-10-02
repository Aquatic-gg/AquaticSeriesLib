package gg.aquatic.aquaticseries.lib.item2.factories

import gg.aquatic.aquaticseries.lib.item2.ItemHandler
import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.adapters.BukkitItemStack
import org.bukkit.inventory.ItemStack

object MMFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return (MythicProvider.get().itemManager.getItem(id).get()
            .generateItemStack(1) as BukkitItemStack).build()
    }
}