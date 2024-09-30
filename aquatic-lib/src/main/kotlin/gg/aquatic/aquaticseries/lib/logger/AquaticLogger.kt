package gg.aquatic.aquaticseries.lib.logger

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import gg.aquatic.aquaticseries.lib.logger.type.DebugLogger
import gg.aquatic.aquaticseries.lib.logger.type.InfoLogger

object AquaticLogger {

    val id: String
        get() {
            return AquaticSeriesLib.INSTANCE.plugin.name
        }
    var debugEnabled = false
}

fun logInfo(message: String) {
    InfoLogger.send(message)
}

fun logDebug(message: String) {
    DebugLogger.send(message)
}