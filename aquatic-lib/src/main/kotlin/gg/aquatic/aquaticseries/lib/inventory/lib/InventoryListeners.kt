package gg.aquatic.aquaticseries.lib.inventory.lib

import gg.aquatic.aquaticseries.lib.util.call
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryCloseEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryInteractEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryOpenEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class InventoryListeners : Listener {

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        val inv = event.inventory
        val holder = inv.holder ?: return

        if (holder is CustomInventory) {
            val e = CustomInventoryClickEvent(holder, event)
            e.call()
            holder.onClick(e)

            if (event.isCancelled && event.rawSlot >= holder.inventory.size) {
                holder.componentHandler.redrawComponent(event.rawSlot)
            }
        }
    }

    @EventHandler
    fun onInvInteract(event: InventoryInteractEvent) {
        val inv: Inventory = event.inventory
        val holder: InventoryHolder = inv.holder ?: return
        if (holder is CustomInventory) {
            val e = CustomInventoryInteractEvent(holder, event)
            e.call()
            holder.onInteract(e)
        }
    }

    @EventHandler
    fun onInvOpen(event: InventoryOpenEvent) {
        val inv: Inventory = event.inventory
        val holder: InventoryHolder = inv.holder ?: return
        if (holder is CustomInventory) {
            val e = CustomInventoryOpenEvent(holder, event)
            e.call()
            holder.onOpen(e)
        }
    }

    @EventHandler
    fun onInvClose(event: InventoryCloseEvent) {
        val inv: Inventory = event.inventory
        val holder: InventoryHolder = inv.holder ?: return
        if (holder is CustomInventory) {
            val e = CustomInventoryCloseEvent(holder, event)
            e.call()
            holder.onClose(e)
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        InventoryHandler.inventoryAdapter.injectPlayer(event.player)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        InventoryHandler.inventoryAdapter.ejectPlayer(event.player)
    }

}