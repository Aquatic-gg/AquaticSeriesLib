package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import java.util.function.BiFunction
import java.util.function.Function

abstract class AbstractAction<T> {

    abstract fun run(binder: T, args: Map<String,Any?>, textUpdater: BiFunction<T,String,String>)

    abstract fun arguments(): List<AquaticObjectArgument<*>>

}