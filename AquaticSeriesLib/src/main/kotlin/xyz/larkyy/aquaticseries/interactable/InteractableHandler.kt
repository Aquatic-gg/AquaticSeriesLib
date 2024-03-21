package xyz.larkyy.aquaticseries.interactable

import com.jeff_media.customblockdata.CustomBlockData
import com.ticxo.modelengine.api.events.BaseEntityInteractEvent
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableBreakEvent
import xyz.larkyy.aquaticseries.interactable.event.BlockInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.event.MegInteractableInteractEvent
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.meg.MEGInteractable
import xyz.larkyy.aquaticseries.interactable.impl.meg.MegInteractableDummy
import xyz.larkyy.aquaticseries.interactable.impl.meg.MegInteractableSerializer
import xyz.larkyy.aquaticseries.interactable.impl.meg.SpawnedMegInteractable
import xyz.larkyy.aquaticseries.toStringSimple

class InteractableHandler {

    val registry = HashMap<String, AbstractInteractable>()
    val spawnedIntectables = HashMap<String, AbstractSpawnedInteractable>()

    // Children Location to Parent Location
    val spawnedChildren = HashMap<String, String>()
    val serializers = HashMap<Class<out AbstractInteractable>, AbstractInteractableSerializer<*>>().apply {
        this[BlockInteractable::class.java] = BlockInteractableSerializer()
        this[MEGInteractable::class.java] = MegInteractableSerializer()
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
    }

    fun loadBlocks(chunk: Chunk) {
        val blocks = CustomBlockData.getBlocksWithCustomData(AquaticSeriesLib.INSTANCE.plugin, chunk)
        for (block in blocks) {
            val data = AbstractSpawnedInteractable.get(block) ?: continue
            val interactable = registry[data.id]
            if (interactable == null) {
                Bukkit.broadcastMessage("Could not find interactable in the registry")
                continue
            }
            val location = block.location.clone()
            location.yaw = data.yaw
            location.pitch = location.pitch

            interactable.onChunkLoad(data,location)
        }
    }

    fun unloadChunk(chunk: Chunk) {

    }

    fun getInteractable(block: Block): AbstractSpawnedInteractable? {
        val location = block.location
        val children = spawnedChildren[location.toStringSimple()] ?: return null
        return spawnedIntectables[children]
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
            val blocks = CustomBlockData.getBlocksWithCustomData(AquaticSeriesLib.INSTANCE.plugin, event.chunk)
            for (block in blocks) {
                val loc = block.location.toStringSimple()
                val parentLoc = spawnedChildren[loc] ?: return
                val spawnedInteractable = spawnedIntectables[parentLoc] ?: return
                //if (spawnedInteractable !is SpawnedBlockInteractable) return
                for (associatedLocation in spawnedInteractable.associatedLocations) {
                    spawnedChildren.remove(associatedLocation.toStringSimple())
                }
                spawnedIntectables.remove(parentLoc)
                if (spawnedInteractable is SpawnedMegInteractable) {
                    spawnedInteractable.destroyEntity()
                }
            }
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
            blockInteractable.interactable.onInteract(BlockInteractableInteractEvent(event,blockInteractable))
        }

        @EventHandler
        fun onBlockBreak(event: BlockBreakEvent) {
            val block = event.block
            val blockInteractable = getBlockInteractable(block) ?: return
            blockInteractable.interactable.onBreak(BlockInteractableBreakEvent(event,blockInteractable))
        }
    }

}