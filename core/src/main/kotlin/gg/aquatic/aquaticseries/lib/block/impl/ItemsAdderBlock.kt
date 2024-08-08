package gg.aquatic.aquaticseries.lib.block.impl

import dev.lone.itemsadder.api.CustomBlock
import org.bukkit.Location
import gg.aquatic.aquaticseries.block.AquaticBlock

class ItemsAdderBlock(
    val iaId: String
): AquaticBlock() {
    override fun place(location: Location) {
        val customBlock: CustomBlock = CustomBlock.getInstance(iaId) ?: return
        customBlock.place(location)
    }
}