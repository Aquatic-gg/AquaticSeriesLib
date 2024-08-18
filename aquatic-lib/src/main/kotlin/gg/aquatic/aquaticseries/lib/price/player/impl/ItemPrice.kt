package gg.aquatic.aquaticseries.lib.price.player.impl

import gg.aquatic.aquaticseries.lib.item.CustomItem
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import org.bukkit.Material
import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.price.player.AbstractPlayerPrice
import gg.aquatic.aquaticseries.lib.util.argument.impl.ItemObjectArgument
import gg.aquatic.aquaticseries.lib.util.toCustomItem

class ItemPrice : AbstractPlayerPrice() {
    override fun take(binder: Player, arguments: Map<String, Any?>) {
        val item = (arguments["item"]!! as CustomItem).getItem()
        var amount = item.amount
        for (content in binder.inventory.contents) {
            content ?: continue
            if (!content.isSimilar(item)) continue

            if (content.amount >= amount) {
                content.amount -= amount
                break
            } else {
                amount -= content.amount
                content.amount = 0
            }
        }
    }

    override fun give(binder: Player, arguments: Map<String, Any?>) {
        val item = (arguments["item"]!! as CustomItem).getItem()
        val toDrop = binder.inventory.addItem(item)
        for (value in toDrop.values) {
            binder.location.world!!.dropItem(binder.location, value)
        }
    }

    override fun set(binder: Player, arguments: Map<String, Any?>) {

    }

    override fun has(binder: Player, arguments: Map<String, Any?>): Boolean {
        val item = (arguments["item"]!! as CustomItem).getItem()
        var amount = item.amount
        for (content in binder.inventory.contents) {
            content ?: continue
            if (!content.isSimilar(item)) continue

            if (content.amount >= amount) {
                return true
            } else {
                amount -= content.amount
            }
        }
        return false
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return arrayListOf(
            ItemObjectArgument("item", Material.STONE.toCustomItem(), true)
        )
    }
}