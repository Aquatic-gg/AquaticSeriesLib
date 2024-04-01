package xyz.larkyy.aquaticfarming.harvestable.loottable

import xyz.larkyy.aquaticfarming.harvestable.condition.loot.ConfiguredLootCondition
import xyz.larkyy.aquaticseries.RangeAmount
import xyz.larkyy.aquaticseries.chance.IChance

class LootTable(
    val chance: Double,
    val conditions: MutableList<ConfiguredLootCondition>,
    val dropsAmount: RangeAmount,
    val drops: MutableList<LootTableDrop>
): IChance {
    override fun chance(): Double {
        return chance
    }
}