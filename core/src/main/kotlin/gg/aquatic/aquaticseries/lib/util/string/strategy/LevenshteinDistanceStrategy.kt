package gg.aquatic.aquaticseries.lib.util.string.strategy

import gg.aquatic.aquaticseries.lib.util.string.SimilarityStrategy
import java.util.*
import kotlin.math.max
import kotlin.math.min

class LevenshteinDistanceStrategy: SimilarityStrategy {

    override fun score(first: String, second: String): Double {
        val maxLength = max(first.length.toDouble(), second.length.toDouble()).toInt()
        //Can't divide by 0
        if (maxLength == 0) return 1.0
        return ((maxLength - computeEditDistance(first, second)).toDouble()) / maxLength.toDouble()
    }

    private fun computeEditDistance(first: String, second: String): Int {
        var first = first
        var second = second
        first = first.lowercase(Locale.getDefault())
        second = second.lowercase(Locale.getDefault())

        val costs = IntArray(second.length + 1)
        for (i in 0..first.length) {
            var previousValue = i
            for (j in 0..second.length) {
                if (i == 0) {
                    costs[j] = j
                } else if (j > 0) {
                    var useValue = costs[j - 1]
                    if (first[i - 1] != second[j - 1]) {
                        useValue =
                            (min(min(useValue.toDouble(), previousValue.toDouble()), costs[j].toDouble()) + 1).toInt()
                    }
                    costs[j - 1] = previousValue
                    previousValue = useValue
                }
            }
            if (i > 0) {
                costs[second.length] = previousValue
            }
        }
        return costs[second.length]
    }

}