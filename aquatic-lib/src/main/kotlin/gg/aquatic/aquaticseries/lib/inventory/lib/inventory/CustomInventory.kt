package gg.aquatic.aquaticseries.lib.inventory.lib.inventory

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.inventory.lib.InventoryHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import gg.aquatic.aquaticseries.lib.inventory.lib.component.ComponentHandler
import gg.aquatic.aquaticseries.lib.inventory.lib.component.ViewOpenComponent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryCloseEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryInteractEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryOpenEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.history.InventoryHistory
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHolder
import gg.aquatic.aquaticseries.lib.toAquatic
import java.util.function.Consumer

open class CustomInventory : InventoryHolder {

    var titleHolder: TitleHolder
    private val inventory: Inventory
    var componentHandler: ComponentHandler = ComponentHandler(this)
    val history: InventoryHistory = InventoryHistory()
    val content = ArrayList<ItemStack?>()
    var cancelEvents = false

    var onOpen: Consumer<CustomInventoryOpenEvent>? = null
    var onClose: Consumer<CustomInventoryCloseEvent>? = null
    var onClick: Consumer<CustomInventoryClickEvent>? = null
    var onInteract: Consumer<CustomInventoryInteractEvent>? = null

    constructor(titleHolder: TitleHolder, size: Int, type: InventoryType, factory: Consumer<CustomInventory>?) {
        this.titleHolder = titleHolder
        inventory =
            if (size > 0)
                AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create("".toAquatic(),this, size)
            else
                AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create("".toAquatic(),this, type)
        for (i in 0..<90) {
            content.add(i, null)
        }
        factory?.accept(this)
    }

    constructor(titleHolder: TitleHolder, size: Int, factory: Consumer<CustomInventory>?) {
        this.titleHolder = titleHolder
        inventory = AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create("".toAquatic(),this, size)

        for (i in 0..<90) {
            content.add(i, null)
        }
        factory?.accept(this)
    }

    constructor(titleHolder: TitleHolder, type: InventoryType, factory: Consumer<CustomInventory>?) {
        this.titleHolder = titleHolder
        inventory = AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create("".toAquatic(),this, type)

        for (i in 0..<90) {
            content.add(i, null)
        }
        factory?.accept(this)
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    fun setTitle(holder: TitleHolder) {
        titleHolder = holder
    }

    fun setTitle(holder: TitleHolder, update: Boolean) {
        setTitle(holder)
        if (update) {
            sendTitleUpdate()
        }
    }

    fun sendTitleUpdate() {
        for (viewer in inventory.viewers) {
            if (viewer !is Player) continue
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.sendTitleUpdate(
                viewer, titleHolder.generate(this, viewer)
            )
        }
    }

    open fun open(player: Player, ignoreHistory: Boolean) {
        val openedInventory = player.openInventory.topInventory
        var previousToAdd: CustomInventory? = null
        (openedInventory.holder as? CustomInventory)?.let {
            val playerHistory = it.history.getHistory(player)
            if (playerHistory != null) {
                previousToAdd = if (playerHistory.ignoreHistory) {
                    playerHistory.previousInventory
                } else {
                    it
                }
            }
        }
        history.addToHistory(player, previousToAdd, ignoreHistory)

        for (value in componentHandler.components.values) {
            if (value !is ViewOpenComponent) continue
            value.onViewOpen(this, player)
        }

        val upcomingTitle = titleHolder.generate(this, player)
        InventoryHandler.upcommingTitles += player.uniqueId to upcomingTitle

        player.openInventory(inventory)
    }

    fun setItem(slot: Int, itemStack: ItemStack) {
        val size = inventory.size
        content[slot] = itemStack

        if (slot >= size) {
            for (viewer in inventory.viewers) {
                if (viewer !is Player) continue
                AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(viewer, itemStack, slot)
            }
        } else {
            inventory.setItem(slot, itemStack)
        }
    }

    open fun openPrevious(player: Player) {
        val previous = history.history[player.uniqueId]?.previousInventory
        history.removeFromHistory(player)
        if (previous == null) {
            player.closeInventory()
            return
        }

        val upcomingTitle = previous.titleHolder.generate(previous, player)
        InventoryHandler.upcommingTitles += player.uniqueId to upcomingTitle

        player.openInventory(previous.getInventory())
    }

    fun onClick(event: CustomInventoryClickEvent) {
        if (cancelEvents) {
            event.originalEvent.isCancelled = true
        }
        onClick?.accept(event)
        componentHandler.onClick(event)
    }

    fun onInteract(event: CustomInventoryInteractEvent) {
        onInteract?.accept(event)
    }

    fun onClose(event: CustomInventoryCloseEvent) {
        onClose?.accept(event)
        val player = event.originalEvent.player as? Player ?: return
        object: BukkitRunnable() {
            override fun run() {
                player.updateInventory()
            }
        }.runTaskLater(AbstractAquaticSeriesLib.INSTANCE.plugin,10)
    }

    fun onOpen(event: CustomInventoryOpenEvent) {
        onOpen?.accept(event)
    }

    fun redrawComponents() {
        for (i in 0..<90) {
            content[i] = null
        }
        componentHandler.redrawComponents()
    }

}