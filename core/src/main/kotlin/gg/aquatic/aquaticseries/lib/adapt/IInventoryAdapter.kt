package gg.aquatic.aquaticseries.lib.adapt

import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

interface IInventoryAdapter {

    fun create(title: AquaticString, holder: InventoryHolder, size: Int): Inventory
    fun create(title: AquaticString, holder: InventoryHolder, type: InventoryType): Inventory

}