package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import gg.aquatic.aquaticseries.lib.input.type.chat.ChatInputHandler
import gg.aquatic.aquaticseries.lib.input.type.chat.TextInputResponse
import gg.aquatic.aquaticseries.lib.input.type.chat.TextValidator
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture
import java.util.function.Function

class ChatNumber(
    val name: String,
    val item: ItemStack,
    vararg val validators: TextValidator
): Edit<Number> {
    override fun handle(editable: Editable<Number>, player: Player): CompletableFuture<Number> {

        return ChatInputHandler.create(player, *validators, TextValidator(
            { str ->
                str.toDoubleOrNull() != null
            },
            "Invalid number format!".toAquatic()
        )).thenApply {
            if (it.response != TextInputResponse.Response.SUCCESS) {
                editable.value
            } else {
                it.message!!.toDouble()
            }
        }
    }
}