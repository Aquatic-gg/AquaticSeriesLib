package gg.aquatic.aquaticseries.lib.betterinventory2

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.listener.AbstractPacketListener
import gg.aquatic.aquaticseries.lib.nms.listener.PacketEvent
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import gg.aquatic.aquaticseries.lib.register
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object InventoryHandler : IFeature {
    override val type: Features = Features.INVENTORIES

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        Listeners().register()
        lib.nmsAdapter!!.packetListenerAdapter().registerListener(PacketListeners)

        object : BukkitRunnable() {
            override fun run() {
                for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                    val holder = onlinePlayer.openInventory.topInventory.holder ?: continue
                    if (holder !is AquaticInventory) continue
                    holder.update()
                }
            }
        }.runTaskTimer(lib.plugin, 1, 1)
    }

    class Listeners : Listener {
        @EventHandler
        fun onInvClick(event: InventoryClickEvent) {
            val inv = event.inventory
            val holder = inv.holder ?: return

            if (holder is AquaticInventory) {
                holder.onClick(event)

                if (event.isCancelled && event.rawSlot >= holder.inventory.size) {
                    holder.updateItem(event.whoClicked as Player, event.rawSlot)
                }
            }
        }

        @EventHandler
        fun onInvInteract(event: InventoryInteractEvent) {
            val inv: Inventory = event.inventory
            val holder: InventoryHolder = inv.holder ?: return
            if (holder is AquaticInventory) {
                holder.onInteract.accept(event, holder)
            }
        }

        @EventHandler
        fun onInvClose(event: InventoryCloseEvent) {
            val inv: Inventory = event.inventory
            val holder: InventoryHolder = inv.holder ?: return
            if (holder is AquaticInventory) {
                holder.onClose.accept(event.player as Player, holder)
                object : BukkitRunnable() {
                    override fun run() {
                        (event.player as Player).updateInventory()
                    }
                }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin, 8)
            }
        }
    }

    object PacketListeners : AbstractPacketListener() {

        override fun onClientboundContainerSetContentPacket(event: PacketEvent<WrappedClientboundContainerSetContentPacket>) {
            val openedInventory = event.player.openInventory.topInventory
            val customInventory = openedInventory.holder as? AquaticInventory ?: return
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
            if (openedInventory.holder !is AquaticInventory) return
            val size = openedInventory.size
            if (event.packet.slot >= size) {
                event.cancelled = true
            }
        }

    }
}