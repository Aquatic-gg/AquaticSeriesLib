package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.action.ConfiguredAction
import gg.aquatic.aquaticseries.lib.requirement.ConfiguredRequirement
import java.util.function.BiFunction

inline fun <T,A,B> Collection<T>.mapPair(processor: (T) -> Pair<A,B>): MutableMap<A,B> {
    val map = mutableMapOf<A,B>()
    this.forEach {
        map += processor(it)
    }
    return map
}

inline fun <reified T: Any> Collection<ConfiguredRequirement<T>>.checkRequirements(binder: T): Boolean {
    for (configuredRequirement in this) {
        if (!configuredRequirement.check(binder)) return false
    }
    return true
}
inline fun <reified T: Any> Collection<ConfiguredAction<T>>.executeActions(binder: T, textUpdater: BiFunction<T,String,String>) {
    for (configuredAction in this) {
        configuredAction.run(binder, textUpdater)
    }
}

inline fun <T,A,B> Collection<T>.mapPairNotNull(processor: (T) -> Pair<A,B>?): MutableMap<A,B> {
    val map = mutableMapOf<A,B>()
    for (it in this) {
        map += processor(it) ?: continue
    }
    return map
}