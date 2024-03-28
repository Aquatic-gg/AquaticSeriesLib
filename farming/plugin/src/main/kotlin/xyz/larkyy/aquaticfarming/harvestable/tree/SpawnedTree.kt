package xyz.larkyy.aquaticfarming.harvestable.tree

import com.google.gson.JsonObject
import org.bukkit.persistence.PersistentDataType
import xyz.larkyy.aquaticfarming.harvestable.SpawnedHarvestable
import xyz.larkyy.aquaticfarming.harvestable.stage.TreeStage
import xyz.larkyy.aquaticseries.interactable.impl.block.SpawnedBlockInteractable
import java.util.UUID

class SpawnedTree(
    override val uuid: UUID,
    val tree: TreeHarvestable,
    var interactable: SpawnedBlockInteractable
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
            stage++
            growthTick = 0
            interactable.destroyBlocks()
            interactable.despawn()
            this.interactable = nextStage.interactable.spawn(interactable.location)
            interactable.spawn(null, true)
            updateData()
        }
        else if (growthTick % 100 == 0) {
            updateData()
        }
    }

    fun nextStage(): TreeStage? {
        if (stage+1 >= tree.stages.size) return null
        return tree.stages[stage + 1]
    }

    fun updateData() {
        val pdc = interactable.data

        val obj = JsonObject()
        obj.addProperty("uuid", uuid.toString())
        obj.addProperty("stage", stage)
        obj.addProperty("growth-tick", growthTick)

        pdc.set(tree.cropNamespace, PersistentDataType.STRING, obj.toString())
    }
}