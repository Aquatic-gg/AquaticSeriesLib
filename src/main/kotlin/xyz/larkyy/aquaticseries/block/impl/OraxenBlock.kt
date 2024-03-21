package xyz.larkyy.aquaticseries.block.impl

import io.th0rgal.oraxen.api.OraxenBlocks
import org.bukkit.Location
import xyz.larkyy.aquaticseries.block.AquaticBlock

class OraxenBlock(
    val oraxenId: String
): AquaticBlock() {
    override fun place(location: Location) {
        OraxenBlocks.place(oraxenId,location)
    }
}