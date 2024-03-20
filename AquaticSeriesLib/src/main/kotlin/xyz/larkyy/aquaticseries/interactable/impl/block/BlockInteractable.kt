package xyz.larkyy.aquaticseries.interactable.impl.block

import com.google.gson.Gson
import com.jeff_media.customblockdata.CustomBlockData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Consumer
import org.bukkit.util.Vector
import xyz.larkyy.aquaticseries.*
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable
import xyz.larkyy.aquaticseries.interactable.AbstractInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.InteractableData
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableBreakEvent
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableInteractEvent
import kotlin.collections.ArrayList
import kotlin.math.floor

class BlockInteractable(
    override val id: String,
    val onInteract: Consumer<BlockInteractableInteractEvent>,
    val onBreak: Consumer<BlockInteractableBreakEvent>,
    val shape: BlockShape
) : AbstractInteractable() {

    init {
        AquaticSeriesLib.INSTANCE.interactableHandler.registry[id] = this
    }

    fun onBreak(event: BlockInteractableBreakEvent) {
        onBreak.accept(event)
    }

    fun onInteract(event: BlockInteractableInteractEvent) {
        this.onInteract.accept(event)
    }

    override val serializer: AbstractInteractableSerializer<SpawnedBlockInteractable>
        get() {
            return (AquaticSeriesLib.INSTANCE.interactableHandler.serializers[BlockInteractable::class.java] as AbstractInteractableSerializer<SpawnedBlockInteractable>?)!!
        }

    override fun spawn(location: Location): SpawnedBlockInteractable {
        //despawn()
        val locations = ArrayList<Location>()
        val spawned = SpawnedBlockInteractable(location, this, locations)
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
        val blockData = InteractableData(id, location.yaw, location.pitch, serializer.serialize(spawned))
        cbd.set(INTERACTABLE_KEY, PersistentDataType.STRING, AquaticSeriesLib.GSON.toJson(blockData))

        val mainLocStr = location.toStringDetailed()

        AquaticSeriesLib.INSTANCE.interactableHandler.spawnedIntectables[mainLocStr] = spawned
        for (loc in locations) {
            AquaticSeriesLib.INSTANCE.interactableHandler.spawnedChildren[loc.toStringSimple()] = mainLocStr
        }
        return spawned
    }

    override fun onChunkLoad(data: InteractableData, location: Location) {
        serializer.deserialize(data.data, location, this)
    }

    override fun onChunkUnload(data: InteractableData) {

    }
}