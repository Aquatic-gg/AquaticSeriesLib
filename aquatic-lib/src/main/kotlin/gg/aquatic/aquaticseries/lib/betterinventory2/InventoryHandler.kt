package gg.aquatic.aquaticseries.lib.betterinventory2

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.nms.listener.AbstractPacketListener
import gg.aquatic.aquaticseries.lib.nms.listener.PacketEvent
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetContentPacket
import gg.aquatic.aquaticseries.lib.nms.packet.WrappedClientboundContainerSetSlotPacket
import gg.aquatic.aquaticseries.lib.register
import gg.aquatic.aquaticseries.lib.util.runLaterSync
import gg.aquatic.aquaticseries.lib.util.runSyncTimer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*

object InventoryHandler : IFeature {
    override val type: Features = Features.INVENTORIES

    val viewers = mutableSetOf<UUID>()

    override fun initialize(lib: AquaticSeriesLib) {
        Listeners().register()
        lib.nmsAdapter!!.packetListenerAdapter().registerListener(PacketListeners)

        runSyncTimer(1,1) {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                if (!viewers.contains(onlinePlayer.uniqueId)) continue
                val holder = onlinePlayer.openInventory.topInventory.holder
                if (holder == null) {
                    viewers.remove(onlinePlayer.uniqueId)
                    continue
                }
                if (holder !is AquaticInventory) {
                    viewers.remove(onlinePlayer.uniqueId)
                    continue
                }
                holder.update()
            }
        }
    }

    class Listeners : Listener {

        @EventHandler
        fun onInvOpen(event: InventoryOpenEvent) {
            if (event.inventory.holder !is AquaticInventory) return
            viewers.add(event.player.uniqueId)
        }

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
                runLaterSync(8) {
                    (event.player as Player).updateInventory()
                }
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
                    val contentItem = customInvContent.getOrNull(i)
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