package gg.aquatic.aquaticseries.lib.inventory

import gg.aquatic.aquaticseries.lib.inventory.lib.InventoryHandler
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory
import gg.aquatic.aquaticseries.lib.nms.listener.AbstractPacketListener
import gg.aquatic.aquaticseries.lib.nms.listener.PacketEvent
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundOpenScreenPacket
import gg.aquatic.aquaticseries.paper.adapt.PaperString
import gg.aquatic.aquaticseries.spigot.adapt.SpigotString
import org.bukkit.inventory.ItemStack

class InventoryPacketListener : AbstractPacketListener() {

    override fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {
        val openedInventory = event.player.openInventory.topInventory
        val customInventory = openedInventory.holder as? CustomInventory ?: return
        val size = openedInventory.size
        val customInvContent = customInventory.content
        val items = ArrayList<ItemStack>()
        for ((i, item) in event.packet.items.withIndex()) {
            if (i >= size) {
                val contentItem = customInvContent[i]
                if (contentItem == null) {
                    items.add(item)
                } else {
                    items.add(contentItem)
                }
            } else {
                items.add(item)
            }
        }
        event.packet.items = items

    }

    override fun onClientboundContainerSetSlotPacket(event: PacketEvent<WrappedClientboundContainerSetSlotPacket>) {
        val openedInventory = event.player.openInventory.topInventory
        if (openedInventory.holder !is CustomInventory) return
        val size = openedInventory.size
        if (event.packet.slot >= size) {
            event.cancelled = true
        }
    }

    override fun onClientboundOpenScreenPacket(event: PacketEvent<WrappedClientboundOpenScreenPacket>) {
        val player = event.player
        val upcomingTitle = InventoryHandler.upcommingTitles.remove(player.uniqueId) ?: return

        event.packet.stringOrJsonTitle = if (upcomingTitle is PaperString) {
            upcomingTitle.toJson()
        } else {
            (upcomingTitle as SpigotString).formatted
        }
    }

}