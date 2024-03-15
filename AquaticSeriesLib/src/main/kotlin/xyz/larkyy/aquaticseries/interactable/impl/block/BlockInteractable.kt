package xyz.larkyy.aquaticseries.interactable.impl.block

import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.Utils
import xyz.larkyy.aquaticseries.block.AquaticBlock
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.InteractableHandler
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.floor
import kotlin.math.round

class BlockInteractable(
    override val location: Location,
    override val onInteract: Consumer<PlayerInteractEvent>,
    val shape: BlockShape
) : AbstractInteractable() {

    val locations = ArrayList<Location>()

    companion object {
        private val INTERACTABLE_BLOCK_KEY =
            NamespacedKey(AquaticSeriesLib.INSTANCE.plugin, "Interactable_Custom_Block")

        fun get(block: Block): BlockInteractable? {
            val cbd = CustomBlockData(block, AquaticSeriesLib.INSTANCE.plugin)
            if (!cbd.has(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING)) return null
            val uuid = cbd.getOrDefault(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING, "null")
            if (uuid == "null") return null
            return BlockInteractableHandler.INSTANCE.placedBlocks[UUID.fromString(uuid)]
        }

    }

    val uuid = UUID.randomUUID()

    override fun spawn() {
        despawn()
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

                    val cbd = CustomBlockData(newLoc.block, AquaticSeriesLib.INSTANCE.plugin)
                    cbd.set(INTERACTABLE_BLOCK_KEY, PersistentDataType.STRING, uuid.toString())
                    x++
                }
            }
        }
        BlockInteractableHandler.INSTANCE.placedBlocks[uuid] = this
    }

    override fun despawn() {
        locations.forEach {
            it.block.type = Material.AIR
            try {
                val cbd = CustomBlockData(it.block,AquaticSeriesLib.INSTANCE.plugin)
                cbd.remove(INTERACTABLE_BLOCK_KEY)
            } catch (_: Exception) {

            }
        }
        locations.clear()
        BlockInteractableHandler.INSTANCE.placedBlocks.remove(uuid)
    }

}