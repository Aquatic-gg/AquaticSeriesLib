package gg.aquatic.aquaticseries.paper

import gg.aquatic.aquaticseries.lib.AquaticLibAdapter
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.adapt.IInventoryAdapter
import gg.aquatic.aquaticseries.lib.adapt.IItemStackAdapter
import gg.aquatic.aquaticseries.paper.adapt.InventoryAdapter
import gg.aquatic.aquaticseries.paper.adapt.ItemStackAdapter
import gg.aquatic.aquaticseries.paper.adapt.PaperString
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class PaperAdapter(override val plugin: JavaPlugin) : AquaticLibAdapter() {

    companion object {
        val minimessage = MiniMessage.miniMessage()
        lateinit var adapter: PaperAdapter
    }

    override val itemStackAdapter: IItemStackAdapter = ItemStackAdapter
    override val inventoryAdapter: IInventoryAdapter = InventoryAdapter

    init {
        adapter = this
    }

    override fun adaptString(string: String): AquaticString {
        return PaperString(string)
    }
}