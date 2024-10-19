package gg.aquatic.aquaticseries.lib.util

fun <E> HashSet<E>.findByHashCode(other: E): E? = firstOrNull { it.hashCode() == other.hashCode() }
fun <E> HashSet<E>.get(index: Int): E? {
    if (index >= this.size) return null
    return this.elementAtOrNull(index)
}