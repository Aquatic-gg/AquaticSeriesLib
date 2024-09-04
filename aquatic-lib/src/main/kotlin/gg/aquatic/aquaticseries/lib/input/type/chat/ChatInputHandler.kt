package gg.aquatic.aquaticseries.lib.input.type.chat

import gg.aquatic.aquaticseries.lib.input.IHandler
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object ChatInputHandler: IHandler {

    val awaiting = ConcurrentHashMap<UUID, ChatInput>()

    fun create(player: Player, vararg validators: TextValidator): CompletableFuture<TextInputResponse> {
        val future = CompletableFuture<TextInputResponse>()
        val input = ChatInput(player.uniqueId, future, *validators)
        return future
    }

    override fun isBeingAwaited(player: Player): Boolean {
        return awaiting.containsKey(player.uniqueId)
    }

    fun onChatEvent(event: AsyncPlayerChatEvent) {
        if (!isBeingAwaited(event.player)) {
            return
        }
        event.isCancelled = true
        val input = awaiting[event.player.uniqueId]!!

        if (event.message.lowercase() == "cancel") {
            input.future.complete(TextInputResponse(TextInputResponse.Response.CANCEL, event.message, null))
            awaiting.remove(event.player.uniqueId)
            return
        }

        for (validator in input.validators) {
            if (!validator.validator.apply(event.message)) {
                validator.errorMessage.send(event.player)
                return
            }
        }
        input.future.complete(TextInputResponse(TextInputResponse.Response.SUCCESS, event.message, null))
        awaiting.remove(event.player.uniqueId)
    }

}