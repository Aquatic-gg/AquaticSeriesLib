package gg.aquatic.aquaticseries.lib.util.string

import gg.aquatic.aquaticseries.lib.util.string.strategy.DiceCoefficientStrategy
import gg.aquatic.aquaticseries.lib.util.string.strategy.JaroStrategy
import gg.aquatic.aquaticseries.lib.util.string.strategy.JaroWinklerStrategy
import gg.aquatic.aquaticseries.lib.util.string.strategy.LevenshteinDistanceStrategy

object SimilarityStrategies {

    val dicesCoefficient = DiceCoefficientStrategy()
    val jaroWinkler = JaroWinklerStrategy()
    val jaro = JaroStrategy()
    val levenshteinDistanceStrategy = LevenshteinDistanceStrategy()

}

fun String.similarity(second: String, strategy: SimilarityStrategy): Double {
    return strategy.score(this, second)
}