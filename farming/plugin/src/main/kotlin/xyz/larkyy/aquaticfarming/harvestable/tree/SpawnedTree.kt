package xyz.larkyy.aquaticfarming.harvestable.tree

import com.google.gson.JsonObject
import org.bukkit.Material
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticfarming.harvestable.SpawnedHarvestable
import xyz.larkyy.aquaticfarming.harvestable.stage.TreeStage
import xyz.larkyy.aquaticseries.AquaticSeriesLib
import xyz.larkyy.aquaticseries.RangeAmount
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable
import java.util.UUID

class SpawnedTree(
    override val uuid: UUID,
    val tree: TreeHarvestable,
    var interactable: SpawnedBlockInteractable,
    val stagesIndex: Int
) : SpawnedHarvestable() {

    var stage: Int = 0
    var growthTick = 0

    init {
        updateData()
    }

    override fun tick() {
        val nextStage = nextStage() ?: return
        growthTick += 5
        if (growthTick >= nextStage.growthDuration*20) {
            if (!canBePlaced(nextStage.interactable)) {
                growthTick=0
            } else {
                stage++
                growthTick = 0
                interactable.destroyBlocks()
                interactable.despawn()
                this.interactable = nextStage.interactable.spawn(interactable.location)
                interactable.spawn(null, true)
                updateData()
            }
        }
        else if (growthTick % 100 == 0) {
            updateData()
        }
    }

    fun canBePlaced(interactable: BlockInteractable): Boolean {
        val ignoredLocations = this.interactable.associatedLocations

        var canPlace = true
        interactable.processLayerCells(interactable.shape.layers, this.interactable.location) { char, newLoc ->
            val i = AquaticSeriesLib.INSTANCE.interactableHandler.getInteractable(newLoc.block)
            for (ignoredLocation in ignoredLocations) {
                if (ignoredLocation.x == newLoc.x && ignoredLocation.y == newLoc.y && ignoredLocation.z == newLoc.z) return@processLayerCells
            }
            if (newLoc.block.type != Material.AIR) {
                if (interactable.shape.blocks[char] != null) {
                    canPlace = false
                }
            } else if (i != null) {
                if (!i.placeCheckEnabled) return@processLayerCells
                if (interactable.shape.blocks[char] != null) {
                    canPlace = false
                }
            }
        }
        return canPlace
    }

    fun nextStage(): TreeStage? {
        if (stage+1 >= tree.stages.size) return null
        return tree.stages[stagesIndex][stage + 1]
    }

    fun updateData() {
        val pdc = interactable.data

        val obj = JsonObject()
        obj.addProperty("uuid", uuid.toString())
        obj.addProperty("stage", stage)
        obj.addProperty("growth-tick", growthTick)
        obj.addProperty("stages-index", stagesIndex)

        pdc.set(tree.cropNamespace, PersistentDataType.STRING, obj.toString())
    }
}