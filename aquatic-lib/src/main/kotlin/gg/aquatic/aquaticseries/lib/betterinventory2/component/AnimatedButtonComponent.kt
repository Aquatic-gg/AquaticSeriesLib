package gg.aquatic.aquaticseries.lib.betterinventory2.component

import gg.aquatic.aquaticseries.lib.betterinventory2.AquaticInventory
import gg.aquatic.aquaticseries.lib.betterinventory2.SlotSelection
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.TreeMap
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function

class AnimatedButtonComponent(
    override val id: String,
    override val priority: Int,
    //override val slotSelection: SlotSelection,
    override val viewConditions: HashMap<Function<Player, Boolean>, InventoryComponent?>,
    override val failItem: InventoryComponent?,
    override var onClick: Consumer<InventoryClickEvent>?,
    override val updateEvery: Int,
    override val textUpdater: BiFunction<Player, String, String>,
    val frames: TreeMap<Int, InventoryComponent>
    //override val item: ItemStack
) : InventoryComponent() {

    var tick = 0
    var currentComponent = frames.values.first()

    override fun getComponent(player: Player, inventory: AquaticInventory): InventoryComponent? {
        var component: InventoryComponent? = null
        var areMet = true
        for (condition in viewConditions.keys) {
            if (!condition.apply(player)) {
                val conditionComponent = viewConditions[condition]
                areMet = false
                if (conditionComponent == null) continue
                if (component != null) {
                    if (component.priority > conditionComponent.priority) continue
                }
                component = viewConditions[condition]
            }
        }
        if (areMet) {
            return currentComponent
        }
        if (failItem == null) return component
        if (component == null) return failItem
        if (component.priority > failItem.priority) return component
        return failItem
    }

    override val slotSelection: SlotSelection
        get() = currentComponent.slotSelection
    override val item: ItemStack
        get() = currentComponent.item

    override fun tick() {
        failItem?.tick()
        viewConditions.values.forEach { it?.tick() }
        val frame = frames[tick]
        if (frame != null) {
            currentComponent = frame
        }
        tick++
        if (tick >= frames.lastKey()) {
            tick = 0
        }
    }

}