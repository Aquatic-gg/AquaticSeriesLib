package gg.aquatic.aquaticseries.lib.nms.packet

class WrappedClientboundDisguisedChatPacket(
    private var _jsonMessage: String
): WrappedPacket() {

    var jsonMessage: String
        get() = _jsonMessage
        set(value) {
            _jsonMessage = value
            modified = true
        }

}