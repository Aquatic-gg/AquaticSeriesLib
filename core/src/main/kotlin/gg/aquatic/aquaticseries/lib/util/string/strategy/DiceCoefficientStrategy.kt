package gg.aquatic.aquaticseries.lib.util.string.strategy

import gg.aquatic.aquaticseries.lib.util.string.SimilarityStrategy
import java.util.*


class DiceCoefficientStrategy: SimilarityStrategy {

    override fun score(first: String, second: String): Double {
        val s1 = splitIntoBigrams(first)
        val s2: Set<String> = splitIntoBigrams(second)

        val n1 = s1.size
        val n2 = s2.size

        s1.retainAll(s2)
        val nt = s1.size
        return (2.0 * nt.toDouble()) / ((n1 + n2).toDouble())
    }


    private fun splitIntoBigrams(s: String): MutableSet<String> {
        val bigrams = ArrayList<String>()

        if (s.length < 2) {
            bigrams.add(s)
        } else {
            for (i in 1 until s.length) {
                val sb = StringBuilder()
                sb.append(s[i - 1])
                sb.append(s[i])
                bigrams.add(sb.toString())
            }
        }
        return TreeSet(bigrams)
    }
}