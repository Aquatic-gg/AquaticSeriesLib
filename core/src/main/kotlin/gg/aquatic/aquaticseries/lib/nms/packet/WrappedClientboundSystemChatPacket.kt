package gg.aquatic.aquaticseries.lib.nms.packet

class WrappedClientboundSystemChatPacket(
    private var _overlay: Boolean,
    private var _jsonMessage: String,
): WrappedPacket() {

    var overlay: Boolean
        get() = _overlay
        set(value) {
            _overlay = value
            modified = true
        }

    var jsonMessage: String
        get() = _jsonMessage
        set(value) {
            _jsonMessage = value
            modified = true
        }
}