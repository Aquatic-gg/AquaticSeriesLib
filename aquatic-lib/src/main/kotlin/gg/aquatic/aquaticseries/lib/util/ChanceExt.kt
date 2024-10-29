package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.chance.ChanceUtils
import gg.aquatic.aquaticseries.lib.chance.IChance

fun <T: IChance> Collection<T>.randomItem(): T? {
    return ChanceUtils.getRandomItem(this)
}