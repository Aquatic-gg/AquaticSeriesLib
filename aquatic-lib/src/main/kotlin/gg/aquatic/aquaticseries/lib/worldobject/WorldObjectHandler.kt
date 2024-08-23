package gg.aquatic.aquaticseries.lib.worldobject

import com.jeff_media.customblockdata.CustomBlockData
import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.worldobject.`object`.*
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import java.util.UUID

object WorldObjectHandler: IFeature {

    val namespacedKeyPrefix = "aquatic-series_world_object"

    val registry = HashMap<String, WorldObject>()
    val spawnedRegistries = HashMap<String, MutableMap<String, ChunkRegistry>>()

    override val type: Features = Features.WORLD_OBJECTS

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        lib.plugin.server.pluginManager.registerEvents(Listeners(), lib.plugin)
    }

    fun registerSpawnedObject(spawned: SpawnedWorldObject<*>) {
        addParent(spawned)

        val child = spawned.children
        for (spawnedWorldChildrenObject in child) {
            addChildren(spawnedWorldChildrenObject)
        }
    }

    fun removeSpawnedObject(spawned: SpawnedWorldObject<*>) {
        removeParent(spawned)

        val child = spawned.children
        for (spawnedWorldChildrenObject in child) {
            removeChildren(spawnedWorldChildrenObject)
        }
    }

    fun getSpawnedObject(location: Location): HashMap<UUID,SpawnedWorldObject<*>> {
        val map = HashMap<UUID,SpawnedWorldObject<*>>()
        map += getParent(location)
        map += getChildren(location).mapValues { it.value.parent }

        return map
    }

    fun getRegistry(chunk: Chunk): ChunkRegistry? {
        val world = spawnedRegistries[chunk.world.name] ?: return null
        val registry = world["${chunk.x};${chunk.z}"]
        return registry
    }

    fun getOrCreateRegistry(chunk: Chunk): ChunkRegistry {
        val world = spawnedRegistries.getOrPut(chunk.world.name) { HashMap() }
        val registry = world.getOrPut("${chunk.x};${chunk.z}") { ChunkRegistry() }
        return registry
    }

    fun removeRegistry(chunk: Chunk) {
        val world = spawnedRegistries[chunk.world.name] ?: return
        world.remove("${chunk.x};${chunk.z}")
    }

    fun addChildren(children: SpawnedWorldChildrenObject<*>) {
        val childrenLoc = children.location
        val registry = getOrCreateRegistry(childrenLoc.chunk)
        val childrenList = registry.children.getOrPut("${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}") { HashMap() }
        childrenList += children.uuid to children
    }

    fun removeChildren(children: SpawnedWorldChildrenObject<*>) {
        val childrenLoc = children.location
        val registry = getRegistry(childrenLoc.chunk) ?: return
        val childrenList = registry.children["${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}"] ?: return
        childrenList.remove(children.uuid)

        if (childrenList.isEmpty()) {
            registry.children.remove("${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}")
        }
    }

    fun getChildren(childrenLoc: Location): HashMap<UUID,SpawnedWorldChildrenObject<*>> {
        val registry = getRegistry(childrenLoc.chunk) ?: return hashMapOf()
        val children = registry.children["${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}"]
        return children ?: hashMapOf()
    }

    fun addParent(spawned: SpawnedWorldObject<*>) {
        val location = spawned.location
        val registry = getOrCreateRegistry(location.chunk)
        val parentList = registry.parents.getOrPut("${location.x};${location.y};${location.z};${location.yaw};${location.pitch}") { HashMap() }
        parentList += spawned.uuid to spawned
    }

    fun getParent(location: Location): HashMap<UUID,SpawnedWorldObject<*>> {
        val registry = getRegistry(location.chunk) ?: return hashMapOf()
        val parentList = registry.parents["${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"] ?: return hashMapOf()
        return parentList
    }

    fun removeParent(spawned: SpawnedWorldObject<*>) {
        val location = spawned.location
        val registry = getRegistry(location.chunk) ?: return
        val parentList = registry.parents["${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"] ?: return
        parentList.remove(spawned.uuid)

        if (parentList.isEmpty()) {
            registry.parents.remove("${location.x};${location.y};${location.z};${location.yaw};${location.pitch}")
        }
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
                    val spawned = getSpawnedObject(block.location)
                    for (s in spawned.values) {
                        s.onUnload()
                        worldObject.onUnload(block.location, s)
                        removeSpawnedObject(s)
                    }
                }
            }
        }
    }
}