package gg.aquatic.aquaticseries.lib.nms.packet

import java.time.Instant
import java.util.UUID

class WrappedClientboundPlayerChatPacket(
    val sender: UUID,
    val originalMessage: String,
    private var _unsignedJsonMessage: String?,
    val salt: Long,
    val timestamp: Instant
): WrappedPacket() {

    var unsignedJsonMessage: String?
        get() = _unsignedJsonMessage
        set(value) {
            _unsignedJsonMessage = value
            modified = true
        }
}