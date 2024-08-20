package gg.aquatic.aquaticseries.lib.fake

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.NMSEntityInteractEvent
import gg.aquatic.aquaticseries.lib.PlayerChunkLoadEvent
import gg.aquatic.aquaticseries.lib.fake.event.PacketBlockInteractEvent
import gg.aquatic.aquaticseries.lib.fake.event.PacketEntityInteractEvent
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.register
import gg.aquatic.aquaticseries.lib.util.call
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.scheduler.BukkitRunnable

object FakeObjectHandler: IFeature {
    override val type: Features = Features.FAKEOBJECTS

    val registry = FakeObjectRegistry()

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        Listeners().register()
    }

    fun registerBlock(packetBlock: PacketBlock) {
        registry.registerBlock(packetBlock)
    }
    fun registerEntity(packetEntity: PacketEntity) {
        registry.registerEntity(packetEntity)
    }

    fun unregisterBlock(location: Location) {
        registry.unregisterBlock(location)
    }
    fun unregisterEntity(location: Location, id: Int) {
        registry.unregisterEntity(location, id)
    }

    fun getBlock(location: Location): PacketBlock? {
        return registry.getBlock(location)
    }

    class Listeners: Listener {

        @EventHandler
        fun PlayerInteractEvent.onInteract() {
            if (hand == EquipmentSlot.OFF_HAND) return
            val block = registry.getBlock(clickedBlock?.location ?: return) ?: return
            val e = PacketBlockInteractEvent(block,action)
            block.onInteract.accept(this)
            isCancelled = true
            e.call()
        }

        @EventHandler
        fun PacketEntityInteractEvent.onEntityInteract() {
            val entity: PacketEntity = packetEntity
            entity.onInteract.accept(this)
        }

        @EventHandler
        fun PlayerChunkLoadEvent.onPlayerChunkLoad() {
            val chunkMap = registry.blocks[player.location.world!!.name]
            val entityChunkMap = registry.blocks[player.location.world!!.name]
            val packetBlocks = chunkMap?.get("${chunk.x};${chunk.z}")
            val packetEntities = entityChunkMap?.get("${chunk.x};${chunk.z}")

            val fakeObjects = ArrayList<AbstractPacketObject>()
            if (packetBlocks != null) fakeObjects.addAll(packetBlocks.values)
            if (packetEntities != null) fakeObjects.addAll(packetEntities.values)

            if (fakeObjects.isNotEmpty()) {
                object : BukkitRunnable() {
                    override fun run() {
                        for (fakeObject in fakeObjects) {
                            if (!fakeObject.spawned) continue
                            if (!fakeObject.audience.currentlyViewing.contains(player.uniqueId)) continue
                            fakeObject.sendSpawnPacket(player)
                        }
                    }
                }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin,10)
            }
        }

        @EventHandler
        fun NMSEntityInteractEvent.onNMSEntityInteract() {
            val entity: PacketEntity = registry.entitiesMapper[entityId] ?: return
            PacketEntityInteractEvent(player, entity, action).call()
        }
    }
}