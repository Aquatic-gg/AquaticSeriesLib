package gg.aquatic.aquaticseries.spigot

import gg.aquatic.aquaticseries.lib.AquaticLibAdapter
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import gg.aquatic.aquaticseries.lib.adapt.ITitleAdapter
import gg.aquatic.aquaticseries.spigot.adapt.InventoryAdapter
import gg.aquatic.aquaticseries.spigot.adapt.ItemStackAdapter
import gg.aquatic.aquaticseries.spigot.adapt.SpigotString
import gg.aquatic.aquaticseries.spigot.adapt.TitleAdapter
import org.bukkit.plugin.java.JavaPlugin

class SpigotAdapter(override val plugin: JavaPlugin) : AquaticLibAdapter() {
    override val itemStackAdapter: IItemStackAdapter = ItemStackAdapter
    override val inventoryAdapter: IInventoryAdapter = InventoryAdapter
    override val titleAdapter: ITitleAdapter = TitleAdapter

    override fun adaptString(string: String): AquaticString {
        return SpigotString(string)
    }
}