package gg.aquatic.aquaticseries.lib.logger

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.logger.type.DebugLogger
import gg.aquatic.aquaticseries.lib.logger.type.InfoLogger

object AquaticLogger {

    val id: String
        get() {
            return AbstractAquaticSeriesLib.INSTANCE.plugin.name
        }
    var debugEnabled = false
}

fun logInfo(message: String) {
    InfoLogger.send(message)
}

fun logDebug(message: String) {
    DebugLogger.send(message)
}