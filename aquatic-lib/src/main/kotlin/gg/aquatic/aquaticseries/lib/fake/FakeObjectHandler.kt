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
import gg.aquatic.aquaticseries.lib.util.runLaterSync
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object FakeObjectHandler : IFeature {
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

    fun getBlock(location: Location): MutableList<PacketBlock> {
        return registry.getBlocks(location)
    }

    class Listeners : Listener {

        @EventHandler
        fun PlayerInteractEvent.onInteract() {
            if (hand == EquipmentSlot.OFF_HAND) return
            val blocks = registry.getBlocks(clickedBlock?.location ?: return)
            if (blocks.isEmpty()) return

            for (block in blocks) {
                if (!block.audience.canBeApplied(player)) continue

                val e = PacketBlockInteractEvent(block, action)
                block.onInteract.accept(this)
                isCancelled = true
                e.call()
            }
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
            packetBlocks?.values?.forEach { pb ->
                for (packetBlock in pb) {
                    if (packetBlock.audience.canBeApplied(player)) fakeObjects.add(packetBlock)
                }
            }
            packetEntities?.values?.forEach { pe ->
                for (packetEntity in pe) {
                    if (packetEntity.audience.canBeApplied(player)) fakeObjects.add(packetEntity)
                }
            }

            if (fakeObjects.isNotEmpty()) {
                runLaterSync(10) {
                    for (fakeObject in fakeObjects) {
                        if (!fakeObject.spawned) continue
                        if (!fakeObject.audience.canBeApplied(player)) continue
                        fakeObject.sendSpawnPacket(player)
                    }
                }
            }
        }

        @EventHandler
        fun NMSEntityInteractEvent.onNMSEntityInteract() {
            val entity: PacketEntity = registry.entitiesMapper[entityId] ?: return
            PacketEntityInteractEvent(player, entity, action).call()
        }
    }
}