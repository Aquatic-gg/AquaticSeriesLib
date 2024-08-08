package gg.aquatic.aquaticseries.interactable

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.Utils
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockShape
import kotlin.math.floor

abstract class AbstractInteractable {

    abstract val id: String
    abstract val serializer: AbstractInteractableSerializer<*>
    abstract val shape: BlockShape

    companion object {
        val INTERACTABLE_KEY =
            NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "Custom_Interactable")

        fun get(block: Block): AbstractInteractable? {
            val data = AbstractSpawnedInteractable.get(block) ?: return null
            return AquaticSeriesLib.INSTANCE.interactableHandler.registry[data.id]
        }
    }

    abstract fun spawn(location: Location): AbstractSpawnedInteractable

    abstract fun onChunkLoad(data: InteractableData, location: Location)
    abstract fun onChunkUnload(data: InteractableData)

    fun processLayerCells(
        layers: MutableMap<Int, MutableMap<Int, String>>,
        location: Location,
        operation: (Char, Location) -> Unit
    ) {
        val face = Utils.cardinalDirection(location.yaw)
        for (layer in layers) {
            val y = layer.key
            val l = layer.value
            for (s in l) {
                val z = s.key
                val chars = s.value.toCharArray()
                if (chars.size % 2 == 0) continue
                var x = -floor(chars.size / 2.0).toInt() - 1
                for (char in chars) {
                    x++
                    val vector = Vector(x, y, z)
                    vector.rotateAroundY(-Math.toRadians(face.ordinal * 90.0))
                    val newLoc = location.block.location.clone().add(vector)

                    operation.invoke(char, newLoc)
                }
            }
        }
    }

    fun canBePlaced(location: Location): Boolean {
        var canPlace = true
        processLayerCells(shape.layers, location) { char, newLoc ->
            if (newLoc.block.type != Material.AIR || AquaticSeriesLib.INSTANCE.interactableHandler.getInteractable(newLoc.block) != null) {
                if (shape.blocks[char] != null) {
                    canPlace = false
                }
            }
        }
        return canPlace
    }
}