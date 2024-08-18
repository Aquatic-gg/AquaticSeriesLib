package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

abstract class AbstractAction<T> {

    abstract fun run(binder: T, args: Map<String,Any?>, placeholders: Placeholders)

    abstract fun arguments(): List<AquaticObjectArgument<*>>

}