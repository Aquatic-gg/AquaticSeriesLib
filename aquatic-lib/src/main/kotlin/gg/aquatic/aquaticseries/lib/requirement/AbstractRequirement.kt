package gg.aquatic.aquaticseries.lib.requirement

import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument


abstract class AbstractRequirement<T> {

    abstract fun check(binder: T, arguments: Map<String,Any?>): Boolean

    abstract fun arguments(): List<AquaticObjectArgument<*>>
}