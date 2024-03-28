package xyz.larkyy.aquaticfarming.harvestable.stage

import xyz.larkyy.aquaticseries.interactable.AbstractInteractable

abstract class AbstractStage {
    abstract val stage: Int
    abstract val interactable: AbstractInteractable
    abstract val growthDuration: Int
}