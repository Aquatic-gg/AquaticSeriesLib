package gg.aquatic.aquaticseries.lib.input

import gg.aquatic.aquaticseries.lib.input.type.chat.TextValidator

abstract class TextInput(
    vararg val validators: TextValidator
) : Input {


    interface Flag {}

}