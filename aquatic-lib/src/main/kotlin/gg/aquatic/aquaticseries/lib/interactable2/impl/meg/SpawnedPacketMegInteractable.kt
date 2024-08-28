package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import gg.aquatic.aquaticseries.lib.block.impl.VanillaBlock
import gg.aquatic.aquaticseries.lib.fake.FakeObjectHandler
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.util.AudienceList
import gg.aquatic.aquaticseries.lib.interactable2.InteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.jvm.optionals.getOrNull

class SpawnedPacketMegInteractable(
    override val audience: AudienceList,
    override val location: Location,
    override val base: MegInteractable<*>,
    override val spawnedInteractableBase: SpawnedInteractableBase<*>
) : AbstractSpawnedPacketInteractable<MegInteractable<*>>(), ISpawnedMegInteractable {

    override val dummy = MegInteractableDummy(this)

    val blocks = HashMap<Location, PacketBlock>()
    override fun show(player: Player) {
        if (handleAudienceShow(player)) return
        for (value in blocks.values) {
            value.sendSpawnPacket(player)
        }
        dummy.setForceViewing(player, true)
        dummy.setForceHidden(player, false)
    }

    override fun hide(player: Player) {
        if (handleAudienceHide(player)) return
        for (value in blocks.values) {
            value.sendDespawnPacket(player)
        }
        dummy.setForceHidden(player, true)
        dummy.setForceViewing(player, false)
    }

    override val associatedLocations: Collection<Location>
        get() {
            return blocks.keys
        }

    init {
        spawnBlocks()
        spawnModel()

        if (audience.mode == AudienceList.Mode.WHITELIST) {
            for (uuid in audience.whitelist) {
                val player = Bukkit.getPlayer(uuid) ?: continue
                dummy.setForceViewing(player, true)
            }
        } else {
            for (uuid in audience.whitelist) {
                val player = Bukkit.getPlayer(uuid) ?: continue
                dummy.setForceHidden(player, true)
            }
        }
    }


    private fun spawnBlocks() {
        base.multiBlock.processLayerCells(location) { char, loc ->
            if (char != ' ') {
                val block = VanillaBlock(Material.AIR.createBlockData())
                val blockData = block.blockData
                val packetBlock = PacketBlock(loc, blockData, AudienceList(mutableListOf(),
                    AudienceList.Mode.BLACKLIST)
                ) {}
                packetBlock.spawn()
                blocks += loc to packetBlock
            }
        }
    }

    private fun spawnModel() {
        dummy.location = this.location
        dummy.bodyRotationController.yBodyRot = location.yaw
        dummy.bodyRotationController.xHeadRot = location.pitch
        dummy.bodyRotationController.yHeadRot = location.yaw
        dummy.yHeadRot = location.yaw
        dummy.yBodyRot = location.yaw
        dummy.isDetectingPlayers = false
        val me = ModelEngineAPI.createModeledEntity(dummy)
        val am = ModelEngineAPI.createActiveModel(base.modelId)
        me.addModel(am,true)
    }

    override val modeledEntity: ModeledEntity?
        get() {
            return ModelEngineAPI.getModeledEntity(dummy.entityId)
        }

    override val activeModel: ActiveModel?
        get() {
            return modeledEntity?.getModel(base.modelId)?.getOrNull()
        }

    override fun despawn() {
        blocks.forEach {
            it.value.despawn()
            FakeObjectHandler.unregisterBlock(it.value.location)
        }
        blocks.clear()

        dummy.isRemoved = true
        modeledEntity?.destroy()
        WorldObjectHandler.removeSpawnedObject(spawnedInteractableBase)
    }

    override fun onInteract(event: InteractableInteractEvent) {
        base.onInteract.accept(this, event)
    }
}