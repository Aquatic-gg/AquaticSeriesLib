package gg.aquatic.aquaticseries.lib.block.impl

import org.bukkit.Location
import org.bukkit.block.data.BlockData
import gg.aquatic.aquaticseries.block.AquaticBlock

class VanillaBlock(
    val blockData: BlockData
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData
    }
}