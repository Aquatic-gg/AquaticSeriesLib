package xyz.larkyy.aquaticfarming.crop.loot

import xyz.larkyy.aquaticseries.chance.ChanceUtils

class CropLootContainer(
    val loots: MutableList<CropLoot>
) {

    fun getRandomLoot(): CropLoot? {
        return ChanceUtils.getRandomItem(loots)
    }

}