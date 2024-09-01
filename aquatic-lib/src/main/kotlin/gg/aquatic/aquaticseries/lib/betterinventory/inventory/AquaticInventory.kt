package gg.aquatic.aquaticseries.lib.betterinventory.inventory

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.betterinventory.component.ComponentHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer

class AquaticInventory(
    val title: AquaticString,
    val size: Int,
    val inventoryType: InventoryType?,
    val onOpen: BiConsumer<Player, AquaticInventory>,
    val onClose: BiConsumer<Player, AquaticInventory>,
    val onInteract: BiConsumer<InventoryInteractEvent, AquaticInventory>
) : InventoryHolder {

    val componentHandler = ComponentHandler(this)
    val content = ArrayList<ItemStack?>()

    private val inventory: Inventory = createInventory()

    private fun createInventory(): Inventory {
        return if (inventoryType != null) AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
            title,
            this,
            inventoryType
        )
        else {
            AbstractAquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
                title,
                this,
                size
            )
        }
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    fun tick() {
        componentHandler.tick()
    }

    fun open(player: Player) {
        player.openInventory(inventory)
        componentHandler.redrawComponents()
        onOpen.accept(player, this)
    }

    fun sendTitleUpdate(newTitle: AquaticString) {
        for (viewer in inventory.viewers) {
            if (viewer !is Player) continue
            AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.sendTitleUpdate(
                viewer, newTitle
            )
        }
    }

    fun onInteract(event: InventoryClickEvent) {

    }
}