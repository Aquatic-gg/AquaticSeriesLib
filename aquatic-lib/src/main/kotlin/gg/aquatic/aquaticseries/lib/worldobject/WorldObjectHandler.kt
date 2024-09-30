package gg.aquatic.aquaticseries.lib.worldobject

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.chunkcache.location.LocationCacheHandler
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.worldobject.`object`.PersistentWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldChildrenObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.SpawnedWorldObject
import gg.aquatic.aquaticseries.lib.worldobject.`object`.WorldObject
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent

object WorldObjectHandler: IFeature {

    val namespacedKeyPrefix = "aquatic-series_world_object"

    val registry = HashMap<String, WorldObject>()

    override val type: Features = Features.WORLD_OBJECTS

    override fun initialize(lib: AquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(), lib.plugin)
    }

    fun registerSpawnedObject(spawned: SpawnedWorldObject<*>) {
        LocationCacheHandler.registerObject(spawned, SpawnedWorldObject::class.java, spawned.location)
        val child = spawned.children
        for (spawnedWorldChildrenObject in child) {
            LocationCacheHandler.registerObject(spawnedWorldChildrenObject, SpawnedWorldChildrenObject::class.java, spawnedWorldChildrenObject.location)
        }
    }

    fun removeSpawnedObject(spawned: SpawnedWorldObject<*>) {
        LocationCacheHandler.unregisterObject(SpawnedWorldObject::class.java, spawned.location)
        val child = spawned.children
        for (spawnedWorldChildrenObject in child) {
            LocationCacheHandler.unregisterObject(SpawnedWorldChildrenObject::class.java, spawnedWorldChildrenObject.location)
        }
    }

    fun getSpawnedObject(location: Location): SpawnedWorldObject<*>? {
        val objects = LocationCacheHandler.getObjects(location)
        val parent = objects[SpawnedWorldObject::class.java]
        if (parent != null) return parent as SpawnedWorldObject<*>
        val children = objects[SpawnedWorldChildrenObject::class.java] as? SpawnedWorldChildrenObject<*> ?: return null
        return children.parent
    }

    class Listeners: Listener {

        @EventHandler
        fun ChunkLoadEvent.onChunkLoad() {
            if (registry.isEmpty()) return

            for ((_, worldObject) in registry) {
                val namespace = worldObject.namespace
                val blocks = CustomBlockData.getBlocksWithCustomData(namespace, chunk)

                worldObject.onChunkLoad(chunk)

                if (worldObject is PersistentWorldObject) {
                    for (block in blocks) {
                        val spawned = worldObject.serializer.load(CustomBlockData(block, namespace))
                        spawned.onLoad()
                        worldObject.onLoad(block.location, spawned)
                        registerSpawnedObject(spawned)
                    }
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
                    val spawned = getSpawnedObject(block.location) ?: continue
                    spawned.onUnload()
                    worldObject.onUnload(block.location, spawned)
                }
            }
        }
    }
}