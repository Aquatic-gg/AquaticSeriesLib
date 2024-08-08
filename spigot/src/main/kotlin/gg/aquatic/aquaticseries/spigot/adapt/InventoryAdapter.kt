package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

object InventoryAdapter: IInventoryAdapter {
    override fun create(title: AquaticString, holder: InventoryHolder, size: Int): Inventory {
        return Bukkit.createInventory(holder, size, if (title is SpigotString) title.formatted else title.string)
    }

    override fun create(title: AquaticString, holder: InventoryHolder, type: InventoryType): Inventory {
        return Bukkit.createInventory(holder, type, if (title is SpigotString) title.formatted else title.string)
    }
}