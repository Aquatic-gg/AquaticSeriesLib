package gg.aquatic.aquaticseries.lib.worldobject

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent

object WorldObjectHandler: IFeature {

    val namespacedKeyPrefix = "aquatic-series_world_object"

    val registry = HashMap<String, WorldObject>()

    override val type: Features = Features.WORLD_OBJECTS

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(), lib.plugin)
    }

    class Listeners: Listener {

        @EventHandler
        fun ChunkLoadEvent.onChunkLoad() {
            if (registry.isEmpty()) return

            for ((_, worldObject) in registry) {
                val namespace = worldObject.namespace
                val blocks = CustomBlockData.getBlocksWithCustomData(namespace, chunk)

                worldObject.onChunkLoad(chunk)

                for (block in blocks) {
                    val spawned = worldObject.serializer.load(CustomBlockData(block, namespace))
                    spawned.onLoad()
                    worldObject.onLoad(block.location, spawned)
                }
            }
        }

        @EventHandler
        fun ChunkUnloadEvent.onChunkUnload() {
            if (registry.isEmpty()) return

            for ((_, worldObject) in registry) {
                val namespace = worldObject.namespace
                val blocks = CustomBlockData.getBlocksWithCustomData(namespace, chunk)

                worldObject.onChunkUnload(chunk)

                for (block in blocks) {
                    val spawned = worldObject.serializer.load(CustomBlockData(block, namespace))
                    spawned.onUnload()
                    worldObject.onUnload(block.location, spawned)
                }
            }
        }
    }
}