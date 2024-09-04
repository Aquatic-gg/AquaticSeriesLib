package gg.aquatic.aquaticseries.lib.input.type.chat

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.entity.Player
import java.util.function.BiFunction
import java.util.function.Function

class TextValidator(
    val validator: Function<String,Boolean>,
    val errorMessage: AquaticString
) {
}