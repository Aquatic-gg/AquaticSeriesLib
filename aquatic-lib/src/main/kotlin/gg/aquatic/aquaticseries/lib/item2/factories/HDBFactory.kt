package gg.aquatic.aquaticseries.lib.item2.factories

import gg.aquatic.aquaticseries.lib.item2.ItemHandler
import me.arcaniax.hdb.api.HeadDatabaseAPI
import org.bukkit.inventory.ItemStack

object HDBFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return HeadDatabaseAPI().getItemHead(id)
    }
}