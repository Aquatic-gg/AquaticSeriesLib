package gg.aquatic.aquaticseries.lib.price

import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument

abstract class AbstractPrice<T> {

    abstract fun take(binder: T, arguments: Map<String,Any?>)
    abstract fun give(binder: T, arguments: Map<String,Any?>)
    abstract fun set(binder: T, arguments: Map<String,Any?>)
    abstract fun has(binder: T, arguments: Map<String,Any?>): Boolean

    abstract fun arguments(): List<AquaticObjectArgument<*>>

}