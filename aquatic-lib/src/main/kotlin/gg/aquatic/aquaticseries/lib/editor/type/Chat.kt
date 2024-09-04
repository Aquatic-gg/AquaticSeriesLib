package gg.aquatic.aquaticseries.lib.editor.type

import gg.aquatic.aquaticseries.lib.editor.Edit
import gg.aquatic.aquaticseries.lib.editor.editable.Editable
import gg.aquatic.aquaticseries.lib.input.type.chat.ChatInputHandler
import gg.aquatic.aquaticseries.lib.input.type.chat.TextInputResponse
import gg.aquatic.aquaticseries.lib.input.type.chat.TextValidator
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture

class Chat(
    val name: String,
    val item: ItemStack,
    vararg val validators: TextValidator
): Edit<String> {
    override fun handle(editable: Editable<String>, player: Player): CompletableFuture<String> {
        return ChatInputHandler.create(player).thenApply {
            if (it.response != TextInputResponse.Response.SUCCESS) {
                editable.value
            } else {
                it.message
            }
        }
    }
}