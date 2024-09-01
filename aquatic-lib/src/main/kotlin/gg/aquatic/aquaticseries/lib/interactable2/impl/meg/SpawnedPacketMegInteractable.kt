package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import com.ticxo.modelengine.api.model.bone.BoneBehaviorTypes
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.BlacklistAudience
import gg.aquatic.aquaticseries.lib.block.impl.VanillaBlock
import gg.aquatic.aquaticseries.lib.fake.FakeObjectHandler
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.interactable2.InteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import gg.aquatic.aquaticseries.lib.worldobject.WorldObjectHandler
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.jvm.optionals.getOrNull

class SpawnedPacketMegInteractable(
    override val audience: AquaticAudience,
    override val location: Location,
    override val base: MegInteractable<*>,
    override val spawnedInteractableBase: SpawnedInteractableBase<*>
) : AbstractSpawnedPacketInteractable<MegInteractable<*>>(), ISpawnedMegInteractable {

    override val dummy = MegInteractableDummy(this)
    override fun setSkin(player: Player) {
        activeModel?.apply {
            for (value in bones.values) {
                value.getBoneBehavior(BoneBehaviorTypes.PLAYER_LIMB).ifPresent {
                    setSkin(player)
                }
            }
        }
    }

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

        updateAudienceView()
    }

    fun updateAudienceView() {
        Bukkit.getOnlinePlayers().forEach { player ->
            dummy.data.tracked.removeForcedHidden(player)
        }
        Bukkit.getOnlinePlayers().forEach { player ->
            dummy.data.tracked.removeForcedPairing(player)
        }
        dummy.data.tracked.trackedPlayer.clear()
        for (uuid in audience.uuids) {
            dummy.setForceViewing(Bukkit.getPlayer(uuid) ?: continue, true)
        }
    }


    private fun spawnBlocks() {
        base.multiBlock.processLayerCells(location) { char, loc ->
            if (char != ' ') {
                val block = VanillaBlock(Material.AIR.createBlockData())
                val blockData = block.blockData
                val packetBlock = PacketBlock(loc, blockData, BlacklistAudience(mutableListOf())) {}
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
        if (canInteract) {
            WorldObjectHandler.removeSpawnedObject(spawnedInteractableBase)
        }
    }

    override fun onInteract(event: InteractableInteractEvent) {
        base.onInteract.accept(this, event)
    }
}