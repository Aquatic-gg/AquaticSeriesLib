package gg.aquatic.aquaticseries.lib.input.type.chat

import gg.aquatic.aquaticseries.lib.input.TextInput
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.function.Function

class ChatInput(
    val playerUUID: UUID,
    val future: CompletableFuture<TextInputResponse>,
    vararg validators: TextValidator,
): TextInput(*validators) {
}