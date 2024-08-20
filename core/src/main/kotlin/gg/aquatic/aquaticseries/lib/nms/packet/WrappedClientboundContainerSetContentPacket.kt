package gg.aquatic.aquaticseries.lib.nms.packet

import org.bukkit.inventory.ItemStack

class WrappedClientboundContainerSetContentPacket(
    private var _containerId: Int,
    private var _stateId: Int,
    private var _items: MutableList<ItemStack>,
    private var _carriedItem: ItemStack?
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
    var items: MutableList<ItemStack>
        get() {
            return _items
        }
        set(value) {
            modified = true
            _items = value
        }
    var carriedItem: ItemStack?
        get() {
            return _carriedItem
        }
        set(value) {
            modified = true
            _carriedItem = value
        }

}