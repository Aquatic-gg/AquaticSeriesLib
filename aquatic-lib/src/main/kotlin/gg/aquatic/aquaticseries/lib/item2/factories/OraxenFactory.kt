package gg.aquatic.aquaticseries.lib.item2.factories

import gg.aquatic.aquaticseries.lib.item2.ItemHandler
import io.th0rgal.oraxen.api.OraxenItems
import org.bukkit.inventory.ItemStack

object OraxenFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return OraxenItems.getItemById(id).build()
    }
}