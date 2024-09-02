package gg.aquatic.aquaticseries.lib.betterinventory2.serialize

import gg.aquatic.aquaticseries.lib.action.ConfiguredAction
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

class ClickSettings(
    val clicks: HashMap<MenuClickActionType,MutableList<ConfiguredAction<Player>>>
) {

    fun handleClick(event: InventoryClickEvent) {
        val type = mapInvAction(event) ?: return
        val actions = clicks[type] ?: return
        for (action in actions) {
            action.run(event.whoClicked as Player, Placeholders())
        }
    }

    companion object {
        private fun mapInvAction(event: InventoryClickEvent): MenuClickActionType? {
            return when (event.click) {
                ClickType.LEFT -> {
                    MenuClickActionType.LEFT
                }
                ClickType.RIGHT -> {
                    MenuClickActionType.RIGHT
                }
                ClickType.SHIFT_LEFT -> {
                    MenuClickActionType.SHIFT_LEFT
                }
                ClickType.SHIFT_RIGHT -> {
                    MenuClickActionType.SHIFT_RIGHT
                }
                ClickType.MIDDLE -> {
                    MenuClickActionType.MIDDLE
                }
                ClickType.NUMBER_KEY -> {
                    when (event.hotbarButton) {
                        0 -> MenuClickActionType.NUM_0
                        1 -> MenuClickActionType.NUM_1
                        2 -> MenuClickActionType.NUM_2
                        3 -> MenuClickActionType.NUM_3
                        4 -> MenuClickActionType.NUM_4
                        5 -> MenuClickActionType.NUM_5
                        6 -> MenuClickActionType.NUM_6
                        7 -> MenuClickActionType.NUM_7
                        8 -> MenuClickActionType.NUM_8
                        9 -> MenuClickActionType.NUM_9
                        else -> MenuClickActionType.NUM_0
                    }
                }
                ClickType.DROP -> {
                    MenuClickActionType.DROP
                }
                ClickType.DOUBLE_CLICK -> {
                    MenuClickActionType.DOUBLE_LEFT
                }
                ClickType.CONTROL_DROP -> {
                    MenuClickActionType.CONTROL_DROP
                }
                ClickType.SWAP_OFFHAND -> {
                    MenuClickActionType.SWAP
                }
                else -> {
                    null
                }
            }
        }
    }

    enum class MenuClickActionType {
        LEFT,
        RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        MIDDLE,
        NUM_0,
        NUM_1,
        NUM_2,
        NUM_3,
        NUM_4,
        NUM_5,
        NUM_6,
        NUM_7,
        NUM_8,
        NUM_9,
        DROP,
        CONTROL_DROP,
        DOUBLE_LEFT,
        SWAP,
    }
}