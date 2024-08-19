package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.ItemObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import gg.aquatic.aquaticseries.lib.util.toCustomItem
import org.bukkit.Material
import org.bukkit.entity.Player

class GiveItemAction: AbstractAction<Player>() {
    override fun run(binder: Player, args: Map<String, Any?>, placeholders: Placeholders) {
        val customItem = args["item"] as? CustomItem ?: return
        customItem.giveItem(binder)
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            ItemObjectArgument("item", Material.STONE.toCustomItem(), true)
        )
    }
}