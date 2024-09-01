package gg.aquatic.aquaticseries.lib.betterinventory.component

import gg.aquatic.aquaticseries.lib.*
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.betterinventory.SlotSelection
import gg.aquatic.aquaticseries.lib.betterinventory.inventory.AquaticInventory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function
import kotlin.collections.HashMap

class InventoryButton(
    override val id: String,
    override val priority: Int,
    val _slotSelection: SlotSelection,
    val item: ItemStack,
    override val onClick: Consumer<InventoryClickEvent>,
    val textUpdater: BiFunction<Player, String, String>,
    override val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>,
    override val failItem: InventoryComponent?,
    val updateEvery: Int = 5
) : InventoryComponent() {

    var updatedItem: ItemStack? = null

    var tick = updateEvery
    override fun tick() {
        if (tick > updateEvery) {
            tick = 0
        } else {
            tick++
        }
    }

    override fun tick(inventory: AquaticInventory, player: Player) {
        if (tick >= updateEvery) {
            updateItem(inventory, player)
        }
    }

    private fun updateItem(inventory: AquaticInventory, player: Player) {
        val component = currentComponent ?: return
        val iS = component.itemStack
        if (iS == null) {
            updatedItem = null
            //inventory.componentHandler.redrawComponent(this)
            return
        }
        val itemMeta = iS.itemMeta
        if (itemMeta == null) {
            updatedItem = iS
            //inventory.componentHandler.redrawComponent(this)
            return
        }

        var updated = false
        val previousName = AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticDisplayName(itemMeta)
        if (previousName != null) {
            val updatedName = textUpdater.apply(player,previousName.string).toAquatic()
            if (previousName.string != updatedName.string) {
                itemMeta.displayName(textUpdater.apply(player,previousName.string).toAquatic())
                updated = true
            }
        }

        val previousLore = AbstractAquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticLore(itemMeta)
        val newLore = ArrayList<AquaticString>()
        for (aquaticString in previousLore) {
            val updatedLore = textUpdater.apply(player,aquaticString.string).toAquatic()
            if (updatedLore.string != aquaticString.string) {
                updated = true
                newLore += updatedLore
            } else {
                newLore += aquaticString
            }
        }
        if (!updated && updatedItem != null) {
            return
        }
        itemMeta.lore(newLore)

        iS.itemMeta = itemMeta
        updatedItem = iS
        inventory.componentHandler.redrawComponent(this)
    }

    override val itemStack: ItemStack?
        get() {
            if (currentComponent == this) {
                return updatedItem
            }
            return currentComponent?.itemStack
        }

    override fun onInteract(event: InventoryClickEvent) {
        val onClick = if (currentComponent == this) {
            this.onClick
        } else {
            currentComponent?.onClick ?: return
        }
        onClick.accept(event)
    }

    override val slotSelection: SlotSelection
        get() {
            if (currentComponent == this) {
                return _slotSelection
            }
            return currentComponent?.slotSelection ?: SlotSelection.of()
        }

}