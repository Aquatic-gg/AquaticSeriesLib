package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

object InventoryAdapter: IInventoryAdapter {
    override fun create(title: AquaticString, holder: InventoryHolder, size: Int): Inventory {
        return Bukkit.createInventory(holder, size, convert(title))
    }

    override fun create(title: AquaticString, holder: InventoryHolder, type: InventoryType): Inventory {
        return Bukkit.createInventory(holder, type, convert(title))
    }

    private fun convert(aquaticString: AquaticString): Component {
        return PaperAdapter.minimessage.deserialize(ChatColor.stripColor(aquaticString.string))
    }
}