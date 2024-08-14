package gg.aquatic.aquaticseries.lib.inventory.lib

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.inventory.lib.component.MenuComponent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.PaginatedInventory
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHolder
import java.util.*
import java.util.function.Consumer

class InventoryUtils {

    companion object {
        fun generatePaginatedInventory(
            player: Player,
            contentComponents: List<MenuComponent>,
            customButtons: List<MenuComponent>,
            availableSlot: SlotSelection,
            previousPage: MenuComponent,
            nextPage: MenuComponent,
            title: TitleHolder,
            size: Int
        ): PaginatedInventory {
            val paginatedMenu = PaginatedInventory(title, size, player, null)

            previousPage.onClick = Consumer {
                paginatedMenu.openPrevious()
            }
            nextPage.onClick = Consumer {
                paginatedMenu.openNextPage()
            }

            val slots = availableSlot.slots.toList()
            val contentSize = slots.size
            var page = paginatedMenu.createPage(title)
            for (customButton in customButtons) {
                page.componentHandler.components += UUID.randomUUID().toString() to customButton
            }
            var pageNum = 0
            for ((i, contentComponent) in contentComponents.withIndex()) {
                if (i-(pageNum*contentSize) == contentSize) {
                    pageNum++
                    page.componentHandler.components += "next-page" to nextPage
                    page = paginatedMenu.createPage(title)
                    for (customButton in customButtons) {
                        page.componentHandler.components += UUID.randomUUID().toString() to customButton
                    }
                    paginatedMenu.addInventoryPage(page)
                    page.componentHandler.components += "previous-page" to previousPage
                }
                contentComponent.slotSelection = SlotSelection.of(slots[(i - (pageNum * contentSize))])
                page.componentHandler.components += "content-$i" to contentComponent
            }
            return paginatedMenu
        }
    }
}