package xyz.larkyy.aquaticseries.block

import org.bukkit.Location
import org.bukkit.block.Block

abstract class AquaticBlock {

    abstract fun place(location: Location)
    abstract fun isBlock(block: Block): Boolean
}