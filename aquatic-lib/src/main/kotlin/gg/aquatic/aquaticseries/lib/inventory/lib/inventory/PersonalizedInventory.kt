package gg.aquatic.aquaticseries.lib.inventory.lib.inventory

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import gg.aquatic.aquaticseries.lib.inventory.lib.title.TitleHolder
import java.util.function.Consumer

open class PersonalizedInventory(title: TitleHolder, size: Int, type: InventoryType, val player: Player, factory: Consumer<PersonalizedInventory>?) :
    CustomInventory(title, size, type, null) {

    init {
        factory?.accept(this)
    }

    constructor(title: TitleHolder, size: Int, player: Player, factory: Consumer<PersonalizedInventory>?) : this(
        title,
        size,
        InventoryType.CHEST,
        player,
        factory
    )

    constructor(
        title: TitleHolder,
        inventoryType: InventoryType,
        player: Player,
        factory: Consumer<PersonalizedInventory>?
    ) : this(
        title,
        0,
        inventoryType,
        player,
        factory
    )

    override fun open(player: Player, ignoreHistory: Boolean) {
        super.open(this.player, ignoreHistory)
    }

    override fun openPrevious(player: Player) {
        super.openPrevious(this.player)
    }

    open fun open(ignoreHistory: Boolean) {
        super.open(player, ignoreHistory)
    }

    fun openPrevious() {
        super.openPrevious(player)
    }

}