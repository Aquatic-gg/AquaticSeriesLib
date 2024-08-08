package gg.aquatic.aquaticseries.lib.block.impl

import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import org.bukkit.Location
import org.bukkit.block.data.BlockData

class VanillaBlock(
    val blockData: BlockData
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData
    }
}