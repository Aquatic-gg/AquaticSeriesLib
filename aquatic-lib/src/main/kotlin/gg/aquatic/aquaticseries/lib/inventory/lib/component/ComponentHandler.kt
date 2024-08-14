package gg.aquatic.aquaticseries.lib.inventory.lib.component

import gg.aquatic.aquaticseries.lib.call
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.event.ComponentClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.ComponentOverrideEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.event.CustomInventoryClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class ComponentHandler(
    val inventory: CustomInventory
) {

    val components = HashMap<String, MenuComponent>()
    val renderedComponents = HashMap<Int, String>()


    fun removeComponent(id: String) {
        components.remove(id)
        for (entry in renderedComponents.toList()) {
            if (entry.second == id) renderedComponents.remove(entry.first)
        }
    }

    fun removeComponent(slot: Int) {
        val id = renderedComponents.remove(slot) ?: return
        components.remove(id)
    }

    fun redrawComponents() {
        inventory.inventory.clear()
        renderedComponents.clear()

        for (menuComponent in components.entries.sortedBy {
            it.value.priority
        }) {
            redrawComponent(menuComponent.key)
        }
    }

    fun getRenderedComponent(slot: Int): MenuComponent? {
        val id = renderedComponents[slot] ?: return null
        return components[id]
    }

    fun redrawComponent(id: String) {
        val component = components[id] ?: return
        val priority = component.priority

        for (slot in component.slotSelection.slots) {
            val originalComponent = getRenderedComponent(slot)
            if (originalComponent != null && priority <= originalComponent.priority && originalComponent != component) {
                continue
            }
            renderedComponents[slot] = id
            if (originalComponent != null) {
                ComponentOverrideEvent(inventory,originalComponent,component).call()
            }
        }

        component.onViewLoad(inventory)
        if (component is ViewOpenComponent) {
            for (viewer in inventory.inventory.viewers) {
                if (viewer is Player) {
                    component.onViewOpen(inventory, viewer)
                }
            }
        }
    }

    fun redrawComponent(slot: Int) {
        val component = getRenderedComponent(slot) ?: return
        component.onViewLoad(inventory)
        if (component is ViewOpenComponent) {
            for (viewer: HumanEntity? in inventory.inventory.viewers) {
                if (viewer is Player) {
                    component.onViewOpen(inventory, viewer)
                }
            }
        }
    }

    fun onClick(e: CustomInventoryClickEvent) {
        val slot = e.originalEvent.rawSlot
        val component = getRenderedComponent(slot) ?: return

        val event = ComponentClickEvent(e.inventory, component, e.originalEvent)
        event.call()

        component.onClick(event)
    }

}