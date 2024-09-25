package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import org.bukkit.entity.Player

fun Player.modifyChatTabCompletion(action: NMSAdapter.TabCompletionAction, list: List<String>) {
    AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.modifyTabCompletion(action, list, this)
}