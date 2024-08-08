package gg.aquatic.aquaticseries.lib.block.impl

import gg.aquatic.aquaticseries.lib.block.AquaticBlock
import io.th0rgal.oraxen.api.OraxenBlocks
import org.bukkit.Location

class OraxenBlock(
    val oraxenId: String
): AquaticBlock() {
    override fun place(location: Location) {
        OraxenBlocks.place(oraxenId,location)
    }
}