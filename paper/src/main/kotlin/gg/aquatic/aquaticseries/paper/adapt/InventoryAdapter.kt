package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
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

    fun convert(aquaticString: AquaticString): Component {
        val legacyComp = LegacyComponentSerializer.legacyAmpersand().deserialize(aquaticString.string)
        val preparedString = LegacyComponentSerializer.legacyAmpersand().serialize(legacyComp)
        return PaperAdapter.minimessage.deserialize(preparedString)
    }
}