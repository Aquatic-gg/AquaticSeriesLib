package xyz.larkyy.aquaticfarming.crop.loot

import xyz.larkyy.aquaticseries.RangeAmount
import xyz.larkyy.aquaticseries.chance.ChanceUtils
import xyz.larkyy.aquaticseries.chance.IChance

class CropLoot(
    val amount: RangeAmount,
    val chance: Double,
    val lootItems: MutableList<CropLootItem>
): IChance {

    override fun chance(): Double {
        return chance
    }

    fun getRandomLootItem(): CropLootItem? {
        return ChanceUtils.getRandomItem(lootItems)
    }

}