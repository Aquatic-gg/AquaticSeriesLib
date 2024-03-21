package xyz.larkyy.aquaticseries

import org.bukkit.block.BlockFace

class Utils {

    companion object {
        fun cardinalDirection(direction: Float): BlockFace {

            var rotation: Float = (direction - 180) % 360
            if (rotation < 0) {
                rotation += 360.0f
            }

            return when {
                rotation >= 315 || rotation < 45 -> BlockFace.NORTH
                rotation >= 45 && rotation < 135 -> BlockFace.EAST
                rotation >= 135 && rotation < 225 -> BlockFace.SOUTH
                else -> BlockFace.WEST
            }
        }
    }

}