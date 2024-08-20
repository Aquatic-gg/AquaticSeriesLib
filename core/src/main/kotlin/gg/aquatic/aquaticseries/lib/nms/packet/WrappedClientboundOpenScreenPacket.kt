package gg.aquatic.aquaticseries.lib.nms.packet

class WrappedClientboundOpenScreenPacket(
    private var _containerId: Int,
    private var _type: Int,
    private var _stringOrJsonTitle: String,
) : WrappedPacket() {

    var containerId: Int
        get() {
            return _containerId
        }
        set(value) {
            modified = true
            _containerId = value
        }
    var type: Int
        get() {
            return _type
        }
        set(value) {
            modified = true
            _type = value
        }
    var stringOrJsonTitle: String
        get() {
            return _stringOrJsonTitle
        }
        set(value) {
            modified = true
            _stringOrJsonTitle = value
        }

}