package gg.aquatic.aquaticseries.lib.logger.type

import gg.aquatic.aquaticseries.lib.format.color.ColorUtils
import gg.aquatic.aquaticseries.lib.logger.AquaticLogger
import gg.aquatic.aquaticseries.lib.logger.ILogger
import org.bukkit.Bukkit

object InfoLogger : ILogger {
    override fun send(message: String) {
        val prefix = "[${AquaticLogger.id}]"
        Bukkit.getConsoleSender().sendMessage(ColorUtils.format("$prefix $message"))
    }
}