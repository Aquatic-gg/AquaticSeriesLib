package gg.aquatic.aquaticseries.lib.requirement

abstract class AbstractRequirement<T> {

    abstract fun check(binder: T, arguments: Map<String,Any?>): Boolean

    abstract fun arguments(): List<RequirementArgument>
}