package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.requirement.RequirementArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders

abstract class AbstractAction<T> {

    abstract fun run(binder: T, args: Map<String,Any?>, placeholders: Placeholders)

    abstract fun arguments(): List<RequirementArgument>

}