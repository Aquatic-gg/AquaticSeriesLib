package gg.aquatic.aquaticseries.lib.util

import org.bukkit.util.Vector
import kotlin.math.max
import kotlin.math.min

class CuboidVector(
    blockPos1: BlockPosition,
    blockPos2: BlockPosition
) {

    val point1: Vector = Vector(blockPos1.x, blockPos1.y, blockPos1.z)
    val point2: Vector = Vector(blockPos2.x, blockPos2.y, blockPos2.z)

    fun rotateAroundY(angle: Double) {
        point1.rotateAroundY(angle)
        point2.rotateAroundY(angle)
    }

    fun generateBlockVectors(): List<Vector> {
        val blockVectors = ArrayList<Vector>()

        generateAndProcess {
            blockVectors.add(it)
        }
        return blockVectors
    }

    fun generateAndProcess(processor: (Vector) -> Unit) {
        val minX: Int = min(point1.blockX.toDouble(), point2.blockX.toDouble()).toInt()
        val maxX: Int = max(point1.blockX.toDouble(), point2.blockX.toDouble()).toInt()
        val minY: Int = min(point1.blockY.toDouble(), point2.blockY.toDouble()).toInt()
        val maxY: Int = max(point1.blockY.toDouble(), point2.blockY.toDouble()).toInt()
        val minZ: Int = min(point1.blockZ.toDouble(), point2.blockZ.toDouble()).toInt()
        val maxZ: Int = max(point1.blockZ.toDouble(), point2.blockZ.toDouble()).toInt()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    processor(Vector(x, y, z))
                }
            }
        }
    }
}