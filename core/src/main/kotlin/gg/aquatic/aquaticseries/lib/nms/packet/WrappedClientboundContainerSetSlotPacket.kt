package gg.aquatic.aquaticseries.lib.nms.packet

import org.bukkit.inventory.ItemStack

class WrappedClientboundContainerSetSlotPacket(
    private var _containerId: Int,
    private var _stateId: Int,
    private var _slot: Int,
    private var _itemStack: ItemStack?
) : WrappedPacket() {

    var containerId: Int
        get() {
            return _containerId
        }
        set(value) {
            modified = true
            _containerId = value
        }
    var stateId: Int
        get() {
            return _stateId
        }
        set(value) {
            modified = true
            _stateId = value
        }
    var slot: Int
        get() {
            return _slot
        }
        set(value) {
            modified = true
            _slot = value
        }
    var itemStack: ItemStack?
        get() {
            return _itemStack
        }
        set(value) {
            modified = true
            _itemStack = value
        }
}