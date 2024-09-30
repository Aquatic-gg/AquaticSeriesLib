package gg.aquatic.aquaticseries.lib.betterinventory2

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.betterinventory2.component.InventoryComponent
import gg.aquatic.aquaticseries.lib.displayName
import gg.aquatic.aquaticseries.lib.lore
import gg.aquatic.aquaticseries.lib.toAquatic
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.BiConsumer

open class AquaticInventory(
    val title: AquaticString,
    val size: Int,
    val inventoryType: InventoryType?,
    val onOpen: BiConsumer<Player, AquaticInventory>,
    val onClose: BiConsumer<Player, AquaticInventory>,
    val onInteract: BiConsumer<InventoryInteractEvent, AquaticInventory>
) : InventoryHolder {

    var id: String? = null

    val content = ArrayList<ItemStack?>(90)
    private val inventory: Inventory = createInventory()

    private fun createInventory(): Inventory {
        return if (inventoryType != null) AquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
            title,
            this,
            inventoryType
        )
        else {
            AquaticSeriesLib.INSTANCE.adapter.inventoryAdapter.create(
                title,
                this,
                size
            )
        }
    }

    init {
        for (i in 0..<90) {
            content += null
        }
    }

    fun open(player: Player) {
        player.openInventory(inventory)
        updateComponents(player)
    }

    fun addComponent(component: InventoryComponent) {
        components[component.id] = component
    }

    fun clearComponents() {
        for (value in stateHandlers.values) {
            value.states.clear()
            value.slotToStates.clear()
        }
        components.clear()
    }

    fun removeComponent(component: InventoryComponent) {
        for (viewer in inventory.viewers) {
            if (viewer !is Player) continue
            val stateHandler = stateHandlers[viewer.uniqueId] ?: continue
            val state = stateHandler.states[component.id] ?: continue
            val comp = state.component ?: continue
            val slots = comp.slotSelection.slots
            for (slot in slots) {
                val slotList = stateHandler.slotToStates[slot]
                if (slotList != null) {
                    slotList.remove(component.id)
                    if (slotList.isEmpty()) {
                        stateHandler.slotToStates.remove(slot)
                    }
                }
            }
        }
        components.remove(component.id)
    }

    val components = HashMap<String, InventoryComponent>()
    val stateHandlers = mutableMapOf<UUID, StateHandler>()

    fun update() {
        for (value in components.values) {
            value.tick()
        }
        for (viewer in inventory.viewers) {
            if (viewer !is Player) {
                continue
            }
            updateComponents(viewer)
        }
    }

    fun onClick(event: InventoryClickEvent) {
        val slot = event.rawSlot
        val stateHandler = stateHandlers[event.whoClicked.uniqueId] ?: return
        stateHandler.rendered[slot]?.let {
            val state = stateHandler.states[it] ?: return
            state.component?.onClick?.accept(event)
        }
    }

    fun updateComponents(player: Player) {
        val stateHandler = stateHandlers.getOrPut(player.uniqueId) { StateHandler() }
        val updated = mutableListOf<String>()
        val updatedSlots = mutableSetOf<Int>()

        for (value in components.values) {
            var state = stateHandler.states[value.id]
            if (state != null) {
                state.tick++
                if (state.tick < value.updateEvery) continue
                state.tick = 0
            }
            val generated = value.getComponent(player, this)
            if (generated == null) {
                if (state == null) {
                    state = ComponentState(null, null)
                    stateHandler.states[value.id] = state
                } else {
                    if (state.component != null) {
                        updatedSlots += state.component!!.slotSelection.slots
                    }

                    state.component = null
                    state.itemStack = null

                    updated += value.id
                }
                continue
            }
            val generatedItem = generateItem(generated.item, generated, player)
            if (state == null) {
                stateHandler.states[value.id] = ComponentState(generated, generatedItem)
                val slots = generated.slotSelection.slots
                for (slot in slots) {
                    stateHandler.slotToStates.getOrPut(slot) { ArrayList() } += value.id
                }

                updatedSlots += slots
                updated += value.id
            } else {
                val stateComponent = state.component
                if (stateComponent == null) {

                    state.component = generated
                    state.itemStack = generatedItem
                    val slots = generated.slotSelection.slots
                    for (slot in slots) {
                        stateHandler.slotToStates.getOrPut(slot) { ArrayList() } += value.id
                    }
                    updated += value.id
                } else if (stateComponent != generated) {

                    state.component = generated
                    state.itemStack = generatedItem

                    val previousSlots = stateComponent.slotSelection.slots
                    val newSlots = generated.slotSelection.slots
                    for (slot in previousSlots) {
                        stateHandler.slotToStates[slot]?.remove(value.id)
                    }
                    for (slot in newSlots) {
                        stateHandler.slotToStates.getOrPut(slot) { ArrayList() } += value.id
                    }
                    updatedSlots += newSlots
                    updatedSlots += previousSlots
                    updated += value.id
                } else {
                    val previousItem = state.itemStack

                    val previousSlots = stateComponent.slotSelection.slots
                    val newSlots = generated.slotSelection.slots

                    if (previousItem == null) {
                        state.itemStack = generatedItem

                        for (slot in previousSlots) {
                            stateHandler.slotToStates[slot]?.remove(value.id)
                        }
                        for (slot in newSlots) {
                            stateHandler.slotToStates.getOrPut(slot) { ArrayList() } += value.id
                        }

                        updatedSlots += newSlots
                        updatedSlots += previousSlots
                        updated += value.id
                    } else if (previousItem.isSimilar(generatedItem) && previousItem.amount == generatedItem.amount) {
                        continue
                    } else {
                        for (slot in previousSlots) {
                            stateHandler.slotToStates[slot]?.remove(value.id)
                        }
                        for (slot in newSlots) {
                            stateHandler.slotToStates.getOrPut(slot) { ArrayList() } += value.id
                        }
                        state.itemStack = generatedItem
                        updated += value.id

                        updatedSlots += newSlots
                        updatedSlots += previousSlots
                    }
                }
            }
        }

        val states = stateHandler.states.filter { it.value.component != null }
        val toRender = mutableMapOf<Int, Pair<String, ComponentState>>()

        for (updatedSlot in updatedSlots) {
            val mapped = mutableMapOf<String, ComponentState>()
            val stateIds = stateHandler.slotToStates[updatedSlot] ?: continue
            for (stateId in stateIds) {
                val state = states[stateId] ?: continue
                mapped[stateId] = state
            }
            var highestPriority: Pair<String, ComponentState>? = null
            for ((id, state) in mapped) {
                if (highestPriority == null) {
                    highestPriority = id to state
                    continue
                }
                val component = highestPriority.second.component ?: continue
                val stateComponent = state.component ?: continue
                if (component.priority < stateComponent.priority) {
                    highestPriority = id to state
                }
            }
            if (highestPriority == null) continue
            toRender[updatedSlot] = highestPriority.first to highestPriority.second
        }

        val unchecked = stateHandler.rendered.filter { !components.containsKey(it.value) }
        for (key in unchecked.keys) {
            if (updatedSlots.contains(key)) continue
            updatedSlots += key
        }
        if (toRender.isEmpty() && updatedSlots.isEmpty()) return
        for (unusedSlot in updatedSlots) {
            val pair = toRender[unusedSlot]
            if (pair == null) {
                setItem(player, ItemStack(Material.AIR), unusedSlot)
                stateHandler.rendered.remove(unusedSlot)
                continue
            }
            if (stateHandler.rendered.containsKey(unusedSlot)) {
                val state = pair.second
                val iS = state.itemStack
                val component = state.component
                if (component == null || iS == null) {
                    setItem(player, ItemStack(Material.AIR), unusedSlot)
                    stateHandler.rendered.remove(unusedSlot)
                    continue
                }
                setItem(player, iS, unusedSlot)
                stateHandler.rendered[unusedSlot] = pair.first
            } else {
                val state = pair.second
                val iS = state.itemStack
                val component = state.component
                if (component == null || iS == null) {
                    setItem(player, ItemStack(Material.AIR), unusedSlot)
                    continue
                }
                setItem(player, iS, unusedSlot)
                stateHandler.rendered[unusedSlot] = pair.first
            }
        }
    }

    fun sendTitleUpdate(newTitle: AquaticString) {
        for (viewer in inventory.viewers) {
            if (viewer !is Player) continue
            AquaticSeriesLib.INSTANCE.nmsAdapter!!.sendTitleUpdate(
                viewer, newTitle
            )
        }
    }

    fun updateItem(player: Player, slot: Int) {
        val stateHandler = stateHandlers[player.uniqueId] ?: return
        val stateId = stateHandler.rendered[slot] ?: return
        val state = stateHandler.states[stateId] ?: return
        val item = state.itemStack ?: return
        setItem(player, item, slot)
    }

    private fun setItem(player: Player, itemStack: ItemStack, slot: Int) {
        val size = inventory.size
        if (slot < content.size) {
            content[slot] = itemStack
        }
        if (slot >= size) {
            AquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(player, itemStack, slot)
        } else {
            inventory.setItem(slot, itemStack)
        }
    }

    private fun generateItem(itemStack: ItemStack, component: InventoryComponent, player: Player): ItemStack {
        val iS = itemStack.clone()
        val itemMeta = iS.itemMeta ?: return iS

        val textUpdater = component.textUpdater

        var updated = false
        val previousName = AquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticDisplayName(itemMeta)
        if (previousName != null) {
            val updatedName = textUpdater.apply(player, previousName.string).toAquatic()
            if (previousName.string != updatedName.string) {
                itemMeta.displayName(textUpdater.apply(player, previousName.string).toAquatic())
                updated = true
            }
        }

        val previousLore = AquaticSeriesLib.INSTANCE.adapter.itemStackAdapter.getAquaticLore(itemMeta)
        val newLore = ArrayList<AquaticString>()
        for (aquaticString in previousLore) {
            val updatedLore = textUpdater.apply(player, aquaticString.string).toAquatic()
            if (updatedLore.string != aquaticString.string) {
                updated = true
                newLore += updatedLore
            } else {
                newLore += aquaticString
            }
        }
        if (!updated) {
            return iS
        }
        itemMeta.lore(newLore)

        iS.itemMeta = itemMeta
        return iS
    }

    class StateHandler {
        val rendered = mutableMapOf<Int, String>()
        val states = mutableMapOf<String, ComponentState>()
        val slotToStates = mutableMapOf<Int, MutableList<String>>()
    }

    class ComponentState(
        var component: InventoryComponent?,
        var itemStack: ItemStack?,
        var tick: Int = 0
    )

    override fun getInventory(): Inventory {
        return inventory
    }

}