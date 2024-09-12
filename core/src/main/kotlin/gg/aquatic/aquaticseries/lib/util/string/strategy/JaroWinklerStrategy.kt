package gg.aquatic.aquaticseries.lib.util.string.strategy

import gg.aquatic.aquaticseries.lib.util.string.SimilarityStrategy
import java.util.*

class JaroWinklerStrategy: JaroStrategy(), SimilarityStrategy {

    companion object {
        const val DEFAULT_SCALING_FACTOR: Double = 0.1 // This is the default scaling factor Winkler used.
    }

    private var scalingFactor = 0.0

    fun JaroWinklerStrategy(scalingFactor: Double) {
        var scalingFactor = scalingFactor
        if (scalingFactor > 0.25) {
            scalingFactor = 0.25
        }
        this.scalingFactor = scalingFactor
    }

    fun JaroWinklerStrategy() {
        this.scalingFactor = DEFAULT_SCALING_FACTOR
    }

    override fun score(first: String, second: String): Double {
        val jaro = super.score(first, second)

        val cl = commonPrefixLength(first, second)
        val winkler = jaro + (scalingFactor * cl * (1.0 - jaro))

        return winkler
    }

    private fun commonPrefixLength(first: String, second: String): Int {
        val shorter: String
        val longer: String

        if (first.length > second.length) {
            longer = first.lowercase(Locale.getDefault())
            shorter = second.lowercase(Locale.getDefault())
        } else {
            longer = second.lowercase(Locale.getDefault())
            shorter = first.lowercase(Locale.getDefault())
        }

        var result = 0

        for (i in shorter.indices) {
            if (shorter[i] != longer[i]) {
                break
            }
            result++
        }

        return if (result > 4) 4 else result
    }

}