package gg.aquatic.aquaticseries.lib.input.type.chat

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import java.util.function.Function

class TextValidator(
    val validator: Function<String,Boolean>,
    val errorMessage: AquaticString
) {
}