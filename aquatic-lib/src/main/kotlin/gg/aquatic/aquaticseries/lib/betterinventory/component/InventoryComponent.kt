package gg.aquatic.aquaticseries.lib.betterinventory.component

import gg.aquatic.aquaticseries.lib.betterinventory.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory.inventory.AquaticInventory
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Function

abstract class InventoryComponent {

    abstract val id: String
    abstract val priority: Int
    abstract val slotSelection: SlotSelection

    abstract val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>
    abstract val failItem: InventoryComponent?

    //private val seenBy: HashMap<UUID, List<Int>> = HashMap()

    abstract fun tick()
    abstract val itemStack: ItemStack?

    protected var currentComponent: InventoryComponent? = null

    fun canSee(inventory: AquaticInventory, player: Player): InventoryComponent? {
        var currentComponent: InventoryComponent? = null
        var canSee = true
        for (viewCondition in viewConditions) {
            val condition = viewCondition.key
            val failItem = viewCondition.value
            if (condition.apply(player)) {
                continue
            }
            if (failItem != null) {
                if (currentComponent != null) {
                    if (currentComponent.priority < failItem.priority) {
                        currentComponent = failItem
                    }
                } else {
                    currentComponent = failItem
                }
            }
            canSee = false
        }
        if (!canSee) {
            val fI = this.failItem?.canSee(inventory, player)
            if (fI != null) {
                if (currentComponent == null) {
                    currentComponent = fI
                } else {
                    if (fI.priority > currentComponent.priority) {
                        currentComponent = fI
                    }
                }
            }
            currentComponent = null
        } else {
            currentComponent = this
        }
        //this.currentComponent = currentComponent
        return currentComponent
    }

    fun update(inventory: AquaticInventory, player: Player) {
        currentComponent = canSee(inventory, player)
    }

    /*
    fun show(inventory: AquaticInventory, player: Player, slots: List<Int>) {
        if (!seenBy.contains(player.uniqueId)) {
            seenBy.add(player.uniqueId)
            handleShow(inventory, player)
        } else {
            handleUpdate(inventory, player)
        }
    }

    fun hideAll(inventory: AquaticInventory, player: Player) {
        hide(inventory, player)
        failItem?.hideAll(inventory, player)
    }

    fun hide(inventory: AquaticInventory, player: Player) {
        if (seenBy.contains(player.uniqueId)) {
            seenBy.remove(player.uniqueId)
            removeCacheExtra(player.uniqueId)
            handleHide(inventory, player)
        }
    }

    fun removeFromCacheAll(uuid: UUID) {
        seenBy.remove(uuid)
        removeCacheExtra(uuid)
        failItem?.removeFromCacheAll(uuid)
    }

     */
}