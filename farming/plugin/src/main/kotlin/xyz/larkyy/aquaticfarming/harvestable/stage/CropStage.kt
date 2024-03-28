package xyz.larkyy.aquaticfarming.harvestable.stage

import xyz.larkyy.aquaticfarming.harvestable.stage.AbstractStage
import xyz.larkyy.aquaticseries.interactable.AbstractInteractable

class CropStage(override val stage: Int, override val interactable: AbstractInteractable,
                override val growthDuration: Int
) : AbstractStage() {
}