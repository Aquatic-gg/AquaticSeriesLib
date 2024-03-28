package xyz.larkyy.aquaticfarming.harvestable.stage

import xyz.larkyy.aquaticfarming.harvestable.stage.AbstractStage
import xyz.larkyy.aquaticseries.interactable.impl.block.BlockInteractable

class TreeStage(override val stage: Int, override val interactable: BlockInteractable, override val growthDuration: Int) : AbstractStage() {
}