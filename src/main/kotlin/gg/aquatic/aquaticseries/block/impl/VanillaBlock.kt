package gg.aquatic.aquaticseries.block.impl

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.data.BlockData
import xyz.larkyy.aquaticseries.block.AquaticBlock

class VanillaBlock(
    val blockData: BlockData
): AquaticBlock() {
    override fun place(location: Location) {
        location.block.type = blockData.material
        location.block.blockData = blockData
    }
}