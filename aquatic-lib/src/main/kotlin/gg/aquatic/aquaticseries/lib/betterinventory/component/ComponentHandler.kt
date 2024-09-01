package gg.aquatic.aquaticseries.lib.betterinventory.component

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.betterinventory.inventory.AquaticInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ComponentHandler(
    val inventory: AquaticInventory
) {


    private val components = HashMap<String, InventoryComponent>()
    private val renderedComponents = HashMap<Int, String>()
    private val slotToComponent = HashMap<Int, List<String>>()

    fun addComponent(component: InventoryComponent) {
    }


    fun removeComponent(id: String) {

    }

    fun removeComponent(component: InventoryComponent) {

    }

    fun redrawComponent(component: InventoryComponent) {
        val slots = component.slotSelection
        redrawSlots(*slots.slots.toIntArray())
    }

    fun redrawSlots(vararg slots: Int) {
        val allSlots = mutableListOf<Int>()
        getAssociatedSlots(allSlots, slots.toList())
        val associatedComponents = mutableListOf<InventoryComponent>()
        for (slot in allSlots) {
            val componentIds = slotToComponent[slot] ?: continue
            for (id in componentIds) {
                val component = components[id] ?: continue
                if (!associatedComponents.contains(component)) {
                    associatedComponents.add(component)
                }
            }
        }

        val toRender = HashMap<Int, InventoryComponent>()
        for (component in associatedComponents) {
            for (slot in component.slotSelection.slots) {
                if (toRender.containsKey(slot)) {
                    if (component.priority > toRender[slot]!!.priority) {
                        toRender[slot] = component
                    } else {
                        continue
                    }
                } else {
                    toRender[slot] = component
                }
            }
        }

        for ((slot, component) in toRender) {
            val item = component.itemStack ?: continue
            allSlots -= slot
            setItem(item, slot)
            renderedComponents[slot] = component.id
        }

        for (slot in allSlots) {
            setItem(ItemStack(Material.AIR), slot)
        }
    }

    fun redrawComponents() {
        val toRender = HashMap<Int, InventoryComponent>()
        for ((id,component) in components) {
            val slots = component.slotSelection
            val priority = component.priority

            for (slot in slots.slots) {
                if (toRender.containsKey(slot)) {
                    if (priority > toRender[slot]!!.priority) {
                        toRender[slot] = component
                    } else {
                        continue
                    }
                } else {
                    toRender[slot] = component
                }
            }
        }

        for ((slot, component) in toRender) {
            val item = component.itemStack ?: continue
            setItem(item, slot)
            renderedComponents[slot] = component.id
        }
    }

    private fun getAssociatedSlots(currentSlots: MutableList<Int>, toLookup: List<Int>) {
        val newLookup = mutableListOf<Int>()
        for (i in toLookup) {
            val components = slotToComponent[i] ?: continue
            for (componentId in components) {
                val component = this.components[componentId] ?: continue
                val slots = component.slotSelection.slots
                for (slot in slots) {
                    if (currentSlots.contains(slot)) continue
                    currentSlots += slot
                    if (!newLookup.contains(slot) && !toLookup.contains(slot)) {
                        newLookup += slot
                    }
                }
            }
        }
        if (newLookup.isNotEmpty()) {
            getAssociatedSlots(currentSlots, newLookup)
        }
    }

    private fun setItem(itemStack: ItemStack, slot: Int) {
        val size = inventory.inventory.size
        inventory.content[slot] = itemStack

        if (slot >= size) {
            for (viewer in inventory.inventory.viewers) {
                if (viewer !is Player) continue
                AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(viewer, itemStack, slot)
            }
        } else {
            inventory.inventory.setItem(slot, itemStack)
        }
    }
}