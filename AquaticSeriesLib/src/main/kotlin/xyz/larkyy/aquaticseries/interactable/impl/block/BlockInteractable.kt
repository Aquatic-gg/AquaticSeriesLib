package xyz.larkyy.aquaticseries.interactable.impl.block

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.Utils
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.toStringDetailed
import kotlin.collections.ArrayList
import kotlin.math.floor

class BlockInteractable(
    override val id: String,
    override val onInteract: Consumer<PlayerInteractEvent>,
    val shape: BlockShape
) : AbstractInteractable() {

    companion object {
        private val INTERACTABLE_BLOCK_KEY =
            NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "Interactable_Custom_Block")

        fun get(block: Block): BlockInteractable? {
            val cbd = CustomBlockData(block, AquaticSeriesLib.INSTANCE.plugin)
            if (!cbd.has(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING)) return null
            val id = cbd.getOrDefault(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING, "null")
            if (id == "null") return null
            return BlockInteractableHandler.INSTANCE.registry[id]
        }
    }

    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.registry[id] = this
    }

    override val serializer: AbstractInteractableSerializer<SpawnedBlockInteractable>
        get() {
            return (AquaticSeriesLib.INSTANCE.interactableHandler.serializers[BlockInteractable::class.java] as AbstractInteractableSerializer<SpawnedBlockInteractable>?)!!
        }

    override fun spawn(location: Location): SpawnedBlockInteractable {
        //despawn()
        val locations = ArrayList<Location>()
        val spawned = SpawnedBlockInteractable(location, this,locations)
        Bukkit.broadcastMessage("Spawning")
        val face = Utils.cardinalDirection(location.yaw)
        for (layer in shape.layers) {
            val y = layer.key
            val l = layer.value

            for (s in l) {
                val z = s.key
                val chars = s.value.toCharArray()
                if (chars.size % 2 == 0) continue
                var x = -floor(chars.size / 2.0).toInt()
                for (char in chars) {
                    val block = shape.blocks[char] ?: continue
                    val vector = Vector(x, y, z)

                    vector.rotateAroundY(-Math.toRadians(face.ordinal * 90.0))

                    val newLoc = location.clone().add(vector)
                    block.place(newLoc)
                    locations += newLoc

                    x++
                }
            }
        }

        val cbd = CustomBlockData(location.block, AquaticSeriesLib.INSTANCE.plugin)
        cbd.set(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING, serializer.serialize(spawned))
        AquaticSeriesLib.INSTANCE.interactableHandler.spawnedIntectables[location.toStringDetailed()] = spawned
        return spawned
    }
}