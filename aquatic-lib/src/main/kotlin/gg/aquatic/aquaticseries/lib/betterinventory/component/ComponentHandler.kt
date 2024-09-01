package gg.aquatic.aquaticseries.lib.betterinventory.component

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.betterinventory.inventory.AquaticInventory
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ComponentHandler(
    val inventory: AquaticInventory
) {


    private val components = HashMap<String, InventoryComponent>()
    private val renderedComponents = HashMap<Int, String>()
    //private val slotToComponent = HashMap<Int, ArrayList<String>>()



    fun addComponent(component: InventoryComponent) {
        components[component.id] = component
    }


    fun removeComponent(id: String) {
        val component = components.remove(id) ?: return
        redrawSlots(
            *component.slotSelection.slots.toIntArray()
        )
    }

    fun removeComponent(component: InventoryComponent) {
        components.remove(component.id)
        redrawSlots(
            *component.slotSelection.slots.toIntArray()
        )
    }

    fun clearComponents() {
        this.components.clear()
        this.renderedComponents.clear()
    }

    fun redrawComponent(component: InventoryComponent) {
        val slots = component.slotSelection
        redrawSlots(*slots.slots.toIntArray())
    }

    fun redrawSlots(vararg slots: Int) {
        val allSlots = mutableListOf<Int>()
        getAssociatedSlots(allSlots, slots.toList())
        val slotToComponent = HashMap<Int, ArrayList<String>>()
        for (component in components) {
            for (slot in component.value.slotSelection.slots) {
                val list = slotToComponent.getOrPut(slot) { ArrayList() }
                if (!list.contains(component.component1())) {
                    list.add(component.component1())
                }
            }
        }
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
            component.isUpdated = false
            renderedComponents[slot] = component.id
        }

        for (slot in allSlots) {
            setItem(ItemStack(Material.AIR), slot)
        }
    }

    fun onInteract(event: InventoryClickEvent) {
        val componentId = renderedComponents[event.rawSlot] ?: return
        val component = components[componentId] ?: return

        component.onInteract(event)
    }

    fun redrawComponents() {
        for (i in 0..<90) {
            inventory.content[i] = null
        }

        val toRender = HashMap<Int, InventoryComponent>()
        for ((id, component) in components) {
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
        val slotToComponent = HashMap<Int, ArrayList<String>>()
        for (component in components) {
            for (slot in component.value.slotSelection.slots) {
                val list = slotToComponent.getOrPut(slot) { ArrayList() }
                if (!list.contains(component.component1())) {
                    list.add(component.component1())
                }
            }
        }
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

        Bukkit.broadcastMessage("Setting item to $slot")

        if (slot >= size) {
            for (viewer in inventory.inventory.viewers) {
                if (viewer !is Player) continue
                AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(viewer, itemStack, slot)
            }
        } else {
            inventory.inventory.setItem(slot, itemStack)
        }
    }

    fun tick() {
        for (viewer in inventory.inventory.viewers) {
            if (viewer !is Player) continue
            for (value in components.values) {
                value.update(inventory, viewer)
                value.tick(inventory, viewer)
            }
        }
        for (value in components.values) {
            value.tick()
        }
    }
}