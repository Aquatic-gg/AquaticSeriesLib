package xyz.larkyy.aquaticseries.interactable

import com.jeff_media.customblockdata.CustomBlockData
import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.call
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableBreakEvent
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.event.InteractablesChunkLoadEvent
import xyz.larkyy.aquaticseries.interactable.event.MegInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.meg.MEGInteractable
import xyz.larkyy.aquaticseries.interactable.impl.meg.MegInteractableDummy
import xyz.larkyy.aquaticseries.interactable.impl.meg.MegInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.impl.meg.SpawnedMegInteractable
import xyz.larkyy.aquaticseries.workload.ChunkWorkload
import xyz.larkyy.aquaticseries.workload.ContainerWorkload
import java.util.concurrent.CompletableFuture

class InteractableHandler(
    val workloadDelay: Long
) {

    val registry = HashMap<String, AbstractInteractable>()

    // World Name, Chunk
    val spawnedRegistries = HashMap<String, MutableMap<String, SpawnedRegistry>>()

    val serializers = HashMap<Class<out AbstractInteractable>, AbstractInteractableSerializer<*>>().apply {
        this[BlockInteractable::class.java] = BlockInteractableSerializer()
        this[MEGInteractable::class.java] = MegInteractableSerializer()
    }

    var canRun = false
    val interactableWorkload = ContainerWorkload(ArrayList(), 0)

    fun getRegistry(chunk: Chunk): SpawnedRegistry? {
        val world = spawnedRegistries[chunk.world.name] ?: return null
        val registry = world["${chunk.x};${chunk.z}"]
        return registry
    }

    fun getOrCreateRegistry(chunk: Chunk): SpawnedRegistry {
        val world = spawnedRegistries.getOrPut(chunk.world.name) { HashMap() }
        val registry = world.getOrPut("${chunk.x};${chunk.z}") { SpawnedRegistry() }
        return registry
    }

    fun removeRegistry(chunk: Chunk) {
        val world = spawnedRegistries[chunk.world.name] ?: return
        world.remove("${chunk.x};${chunk.z}")
    }

    fun addChildren(childrenLoc: Location, parentLocation: Location) {
        val registry = getOrCreateRegistry(childrenLoc.chunk)
        registry.children += "${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}" to
                "${parentLocation.world!!.name};${parentLocation.chunk.x};${parentLocation.chunk.z};${parentLocation.x};${parentLocation.y};${parentLocation.z};${parentLocation.yaw};${parentLocation.pitch}"
    }

    fun removeChildren(childrenLoc: Location) {
        val registry = getRegistry(childrenLoc.chunk) ?: return
        registry.children -= "${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}"
    }

    fun getChildren(childrenLoc: Location): String? {
        val registry = getRegistry(childrenLoc.chunk) ?: return null
        val children = registry.children["${childrenLoc.x};${childrenLoc.y};${childrenLoc.z}"]
        return children
    }

    fun addParent(location: Location, interactable: AbstractSpawnedInteractable) {
        val registry = getOrCreateRegistry(location.chunk)
        val id = "${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"
        registry.parents += id to interactable
    }

    fun getParent(location: Location): AbstractSpawnedInteractable? {
        val registry = getRegistry(location.chunk) ?: return null
        return registry.parents["${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"]
    }

    fun getParentByChildren(childrenLoc: Location): AbstractSpawnedInteractable? {
        val children = getChildren(childrenLoc) ?: return null
        val args = children.split(";")
        val world = args[0]
        val chunkMap = spawnedRegistries[world] ?: return null
        val chunk = "${args[1]};${args[2]}"
        val registry = chunkMap[chunk] ?: return null
        val loc = "${args[3]};${args[4]};${args[5]};${args[6]};${args[7]}"
        return registry.parents[loc]
    }

    fun removeParent(location: Location) {
        val registry = getRegistry(location.chunk) ?: return
        val id = "${location.x};${location.y};${location.z};${location.yaw};${location.pitch}"
        registry.parents -= id
    }

    fun addWorkloadJob(chunk: Chunk, runnable: Runnable): CompletableFuture<Void> {
        val workload = ChunkWorkload(chunk, mutableListOf(runnable), workloadDelay)
        interactableWorkload.workloads.add(workload)
        if (!interactableWorkload.isRunning && canRun) {
            return interactableWorkload.run()
        }
        return interactableWorkload.future
    }

    fun registerListeners(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(Listeners(), plugin)

        for (world in Bukkit.getWorlds()) {
            for (loadedChunk in world.loadedChunks) {
                loadChunk(loadedChunk)
            }
        }
    }

    fun loadChunk(chunk: Chunk) {
        loadBlocks(chunk)
        val spawned = ArrayList<AbstractSpawnedInteractable>()
        getRegistry(chunk)?.parents?.values?.forEach {
            spawned.add(it)
        }
        if (spawned.isNotEmpty()) {
            InteractablesChunkLoadEvent(chunk,spawned).call()
        }
    }

    fun loadBlocks(chunk: Chunk) {
        val blocks = CustomBlockData.getBlocksWithCustomData(AquaticSeriesLib.INSTANCE.plugin, chunk)
        for (block in blocks) {
            val data = AbstractSpawnedInteractable.get(block) ?: continue
            val interactable = registry[data.id] ?: continue
            val location = block.location.clone()
            location.yaw = data.yaw
            location.pitch = location.pitch
            interactable.onChunkLoad(data, location)
        }
    }

    fun getSpawned(chunk: Chunk): ArrayList<AbstractSpawnedInteractable> {
        val spawned = ArrayList<AbstractSpawnedInteractable>()
        val blocks = CustomBlockData.getBlocksWithCustomData(AquaticSeriesLib.INSTANCE.plugin, chunk)
        for (block in blocks) {
            val loc = block.location
            val spawnedInteractable = getParentByChildren(loc) ?: continue
            spawned += spawnedInteractable
        }
        return spawned
    }

    fun unloadChunk(chunk: Chunk) {

    }

    fun getInteractable(block: Block): AbstractSpawnedInteractable? {
        val location = block.location
        return getParentByChildren(location)
    }

    fun getBlockInteractable(block: Block): SpawnedBlockInteractable? {
        val spawnedInteractable = getInteractable(block) ?: return null
        if (spawnedInteractable !is SpawnedBlockInteractable) return null
        return spawnedInteractable
    }

    inner class Listeners : Listener {
        @EventHandler
        fun onChunkLoad(event: ChunkLoadEvent) {
            loadChunk(event.chunk)
        }

        @EventHandler
        fun onChunkUnload(event: ChunkUnloadEvent) {
            getRegistry(event.chunk) ?: return
            val blocks = CustomBlockData.getBlocksWithCustomData(AquaticSeriesLib.INSTANCE.plugin, event.chunk)
            for (block in blocks) {
                val loc = block.location
                val spawnedInteractable = getParentByChildren(loc) ?: continue
                for (associatedLocation in spawnedInteractable.associatedLocations) {
                    removeChildren(associatedLocation)
                }
                if (spawnedInteractable is SpawnedMegInteractable) {
                    spawnedInteractable.destroyEntity()
                }
            }
            removeRegistry(event.chunk)
        }

        @EventHandler
        fun onMegInteract(event: BaseEntityInteractEvent) {
            val dummy = event.baseEntity as? MegInteractableDummy ?: return
            val interactable = dummy.spawnedInteractable
            interactable.interactable.onInteract(MegInteractableInteractEvent(event, interactable))
        }

        @EventHandler
        fun onInteract(event: PlayerInteractEvent) {
            val block = event.clickedBlock ?: return
            val blockInteractable = getBlockInteractable(block) ?: return
            blockInteractable.interactable.onInteract(BlockInteractableInteractEvent(event, blockInteractable))
        }

        @EventHandler
        fun onBlockBreak(event: BlockBreakEvent) {
            val block = event.block
            val blockInteractable = getBlockInteractable(block) ?: return
            blockInteractable.interactable.onBreak(BlockInteractableBreakEvent(event, blockInteractable))
        }
    }
}