package gg.aquatic.aquaticseries.lib.inventory.lib.inventory

import gg.aquatic.aquaticseries.lib.inventory.lib.component.ComponentHandler
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHolder

class InventoryPage(
    val inventory: PaginatedInventory,
    var titleHolder: TitleHolder
) {

    val componentHandler = ComponentHandler(inventory)

}