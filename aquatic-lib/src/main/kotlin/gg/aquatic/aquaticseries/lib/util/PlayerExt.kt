package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import org.bukkit.EntityEffect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.modifyChatTabCompletion(action: NMSAdapter.TabCompletionAction, list: List<String>) {
    AquaticSeriesLib.INSTANCE.nmsAdapter!!.modifyTabCompletion(action, list, this)
}

fun Player.playTotemAnimation(modelData: Int) {

    val previousItem = inventory.itemInMainHand

    val item = ItemStack(Material.TOTEM_OF_UNDYING)
    val meta = item.itemMeta!!
    meta.setCustomModelData(modelData)
    item.itemMeta = meta

    AquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(this, item, 45)
    this.playEffect(EntityEffect.TOTEM_RESURRECT)
    AquaticSeriesLib.INSTANCE.nmsAdapter!!.setContainerItem(this, previousItem, 45)
}