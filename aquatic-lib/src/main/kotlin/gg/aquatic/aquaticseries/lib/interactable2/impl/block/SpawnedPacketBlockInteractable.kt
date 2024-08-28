package gg.aquatic.aquaticseries.lib.interactable2.impl.block

import gg.aquatic.aquaticseries.lib.fake.FakeObjectHandler
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.util.AudienceList
import gg.aquatic.aquaticseries.lib.interactable2.InteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import org.bukkit.Location
import org.bukkit.entity.Player

class SpawnedPacketBlockInteractable(
    override val audience: AudienceList,
    override val location: Location,
    override val base: BlockInteractable<*>,
    override val spawnedInteractableBase: SpawnedInteractableBase<*>
) : AbstractSpawnedPacketInteractable<BlockInteractable<*>>() {

    val blocks = HashMap<Location,PacketBlock>()

    init {
        spawnBlocks()
    }

    private fun spawnBlocks() {
        val shape = base.multiBlock.shape
        base.multiBlock.processLayerCells(location) { char, loc ->
            val block = shape.blocks[char]
            if (block != null) {
                val blockData = block.blockData
                val packetBlock = PacketBlock(loc, blockData, audience) {}
                packetBlock.spawn()
                blocks += loc to packetBlock
            }
        }
    }

    override fun show(player: Player) {
        if (handleAudienceShow(player)) return
        for (value in blocks.values) {
            value.sendSpawnPacket(player)
        }
    }

    override fun hide(player: Player) {
        if (handleAudienceHide(player)) return
        for (value in blocks.values) {
            value.sendDespawnPacket(player)
        }
    }

    override val associatedLocations: Collection<Location>
        get() {
            return blocks.keys
        }

    override fun despawn() {
        for (value in blocks.values) {
            value.despawn()
            FakeObjectHandler.registry.unregisterBlock(value.location)
        }
        blocks.clear()
        WorldObjectHandler.removeSpawnedObject(spawnedInteractableBase)
    }

    override fun onInteract(event: InteractableInteractEvent) {
        base.onInteract.accept(this, event)
    }
}