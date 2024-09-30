package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.nms.NMSAdapter
import org.bukkit.entity.Player

fun Player.modifyChatTabCompletion(action: NMSAdapter.TabCompletionAction, list: List<String>) {
    AquaticSeriesLib.INSTANCE.nmsAdapter!!.modifyTabCompletion(action, list, this)
}