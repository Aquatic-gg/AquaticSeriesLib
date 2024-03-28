package xyz.larkyy.aquaticseries.block.impl

import dev.lone.itemsadder.api.CustomBlock
import org.bukkit.Location
import org.bukkit.block.Block
import xyz.larkyy.aquaticseries.block.AquaticBlock

class ItemsAdderBlock(
    val iaId: String
): AquaticBlock() {
    override fun place(location: Location) {
        val customBlock: CustomBlock = CustomBlock.getInstance(iaId) ?: return
        customBlock.place(location)
    }

    override fun isBlock(block: Block): Boolean {
        val customBlock: CustomBlock = CustomBlock.getInstance(iaId) ?: return false
        return (CustomBlock.byAlreadyPlaced(block) == customBlock)
    }
}