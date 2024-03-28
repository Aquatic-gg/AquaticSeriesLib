package xyz.larkyy.aquaticseries.block.impl

import dev.lone.itemsadder.api.CustomBlock
import io.th0rgal.oraxen.api.OraxenBlocks
import org.bukkit.Location
import org.bukkit.block.Block
import xyz.larkyy.aquaticseries.block.AquaticBlock

class OraxenBlock(
    val oraxenId: String
): AquaticBlock() {
    override fun place(location: Location) {
        OraxenBlocks.place(oraxenId,location)
    }

    override fun isBlock(block: Block): Boolean {
        return (OraxenBlocks.getOraxenBlock(block.location).itemID == oraxenId)
    }
}