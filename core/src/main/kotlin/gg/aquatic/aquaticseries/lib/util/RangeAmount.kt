package gg.aquatic.aquaticseries.lib.util

import kotlin.random.Random


class RangeAmount(
    val min: Int,
    val max: Int
) {

    val getRandomAmount: Int
        get() {
            if (min == max) {
                return min
            }
            return Random.nextInt(max - min + 1) + min
        }

}