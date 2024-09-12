package gg.aquatic.aquaticseries.lib.util

inline fun <T,A,B> Collection<T>.mapPair(processor: (T) -> Pair<A,B>): MutableMap<A,B> {
    val map = mutableMapOf<A,B>()
    this.forEach {
        map += processor(it)
    }
    return map
}

inline fun <T,A,B> Collection<T>.mapPairNotNull(processor: (T) -> Pair<A,B>?): MutableMap<A,B> {
    val map = mutableMapOf<A,B>()
    for (it in this) {
        map += processor(it) ?: continue
    }
    return map
}