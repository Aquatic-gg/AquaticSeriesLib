package xyz.larkyy.aquaticfarming.harvestable.loottable

import xyz.larkyy.aquaticseries.RangeAmount
import xyz.larkyy.aquaticseries.chance.IChance
import xyz.larkyy.aquaticseries.item.CustomItem

class LootTableDrop(
    val chance: Double,
    val amount: RangeAmount,
    val item: CustomItem
): IChance {
    override fun chance(): Double {
        return chance
    }
}