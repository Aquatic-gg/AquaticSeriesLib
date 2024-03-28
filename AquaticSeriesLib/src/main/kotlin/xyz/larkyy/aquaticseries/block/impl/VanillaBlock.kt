package xyz.larkyy.aquaticseries.block.impl

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import xyz.larkyy.aquaticseries.block.AquaticBlock

class VanillaBlock(
    val blockData: BlockData
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData
    }

    override fun isBlock(block: Block): Boolean {
        return (blockData.matches(block.blockData))
    }
}