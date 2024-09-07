package gg.aquatic.aquaticseries.lib.logger.type

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.logger.AquaticLogger
import gg.aquatic.aquaticseries.lib.logger.ILogger
import org.bukkit.Bukkit

object DebugLogger: ILogger {
    override fun send(message: String) {
        if (!AquaticLogger.debugEnabled) return
        val prefix = "[DEBUG] [${AquaticLogger.id}]"
        Bukkit.getConsoleSender().sendMessage(ColorUtils.format("$prefix $message"))
    }
}