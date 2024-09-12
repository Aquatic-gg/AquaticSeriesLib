package gg.aquatic.aquaticseries.lib.util.string.strategy

import gg.aquatic.aquaticseries.lib.util.string.SimilarityStrategy
import java.util.*
import kotlin.math.max
import kotlin.math.min

open class JaroStrategy : SimilarityStrategy {
    override fun score(first: String, second: String): Double {
        val shorter: String
        val longer: String

        if (first.length > second.length) {
            longer = first.lowercase(Locale.getDefault())
            shorter = second.lowercase(Locale.getDefault())
        } else {
            longer = second.lowercase(Locale.getDefault())
            shorter = first.lowercase(Locale.getDefault())
        }

        val halflength = (shorter.length / 2) + 1

        val m1 = getSetOfMatchingCharacterWithin(shorter, longer, halflength)
        val m2 = getSetOfMatchingCharacterWithin(longer, shorter, halflength)


        if (m1.isEmpty() || m2.isEmpty()) return 0.0

        if (m1.length != m2.length) return 0.0

        val transpositions = transpositions(m1, m2)

        val dist =
            (m1.length / (shorter.length.toDouble()) + m2.length / (longer.length.toDouble()) + (m1.length - transpositions) / (m1.length.toDouble())) / 3.0
        return dist
    }

    private fun getSetOfMatchingCharacterWithin(first: String, second: String, limit: Int): String {
        val common = StringBuilder()
        val copy = StringBuilder(second)
        for (i in first.indices) {
            val ch = first[i]
            var found = false

            // See if the character is within the limit positions away from the original position of that character.
            var j = max(0.0, (i - limit).toDouble()).toInt()
            while (!found && j < min((i + limit).toDouble(), second.length.toDouble())) {
                if (copy[j] == ch) {
                    found = true
                    common.append(ch)
                    copy.setCharAt(j, '*')
                }
                j++
            }
        }
        return common.toString()
    }

    private fun transpositions(first: String, second: String): Int {
        var transpositions = 0
        for (i in first.indices) {
            if (first[i] != second[i]) {
                transpositions++
            }
        }
        transpositions /= 2
        return transpositions
    }
}