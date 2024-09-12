package gg.aquatic.aquaticseries.lib.util.string

interface SimilarityStrategy {

    fun score(first: String, second: String): Double
}