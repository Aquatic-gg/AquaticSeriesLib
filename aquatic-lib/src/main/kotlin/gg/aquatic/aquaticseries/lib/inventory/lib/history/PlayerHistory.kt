package gg.aquatic.aquaticseries.lib.inventory.lib.history

import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class PlayerHistory(
    val previousInventory: CustomInventory?,
    val ignoreHistory: Boolean
) {

    fun hasPrevious(): Boolean {
        return previousInventory != null
    }

}