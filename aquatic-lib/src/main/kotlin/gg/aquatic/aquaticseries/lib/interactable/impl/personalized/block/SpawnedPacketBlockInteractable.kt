package gg.aquatic.aquaticseries.lib.interactable.impl.personalized.block

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable.AbstractSpawnedPacketInteractable
import gg.aquatic.aquaticseries.lib.interactable.AudienceList
import gg.aquatic.aquaticseries.lib.interactable.InteractableData
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableBreakEvent
import gg.aquatic.aquaticseries.lib.interactable.event.BlockInteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable.impl.global.block.BlockInteractable
import gg.aquatic.aquaticseries.lib.util.toStringSimple
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Consumer

class SpawnedPacketBlockInteractable(
    override val location: Location,
    override val interactable: BlockInteractable,
    var audienceList: AudienceList
): AbstractSpawnedPacketInteractable() {

    override fun show(player: Player) {
        if (audienceList.mode == AudienceList.Mode.BLACKLIST) {
            audienceList.whitelist -= player.uniqueId
        } else {
            audienceList.whitelist += player.uniqueId
        }
    }

    override fun hide(player: Player) {
        if (audienceList.mode == AudienceList.Mode.BLACKLIST) {
            audienceList.whitelist += player.uniqueId
        }
    }

    override val associatedLocations = ArrayList<Location>()

    val blocks = HashMap<String, PacketBlock>()

    val onInteract: Consumer<BlockInteractableInteractEvent>? = null
    val onBreak: Consumer<BlockInteractableBreakEvent>? = null

    override var loaded = false
    var removed = false

    fun spawn(data: InteractableData?, reset: Boolean) {
        if (removed) return
        if (data != null && !reset) {
            for (associatedLocation in associatedLocations) {
                AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
            }
            associatedLocations.clear()
            interactable.processLayerCells(data.previousShape, location) { char, newLoc ->
                associatedLocations += newLoc
            }
        } else {
            if (reset && data != null) {
                interactable.despawnOldData(data, location)
            }

            for (associatedLocation in associatedLocations) {
                AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
            }
            val nullChars = ArrayList<Char>()
            associatedLocations.clear()
            interactable.processLayerCells(interactable.shape.layers, location) { char, newLoc ->
                val block = interactable.shape.blocks[char]
                if (block == null) {
                    nullChars += char
                } else {
                    blocks += newLoc.toStringSimple() to block.placePacket(newLoc, audienceList)
                    //block.place(newLoc)
                    associatedLocations += newLoc
                }
            }
        }
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addParent(location,this)
        for (loc in associatedLocations) {
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.addChildren(loc,location)
        }
        loaded = true
    }

    override fun despawn() {
        removed = true
        for (associatedLocation in associatedLocations) {
            for (value in blocks.values) {
                value.despawn()
            }
            blocks.clear()
            AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeChildren(associatedLocation)
        }
        AbstractAquaticSeriesLib.INSTANCE.interactableHandler!!.removeParent(location)
    }
}