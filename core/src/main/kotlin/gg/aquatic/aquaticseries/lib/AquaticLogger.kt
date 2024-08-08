package gg.aquatic.aquaticseries.lib

import org.bukkit.Bukkit

class AquaticLogger {


    private inner class ErrorLogger: gg.aquatic.aquaticseries.lib.AquaticLogger.AbstractAquaticLogger() {
        override fun log(message: String) {
            Bukkit.getConsoleSender().sendMessage("§c[ERROR] $message")
        }
    }

    private inner class InfoLogger: _root_ide_package_.gg.aquatic.aquaticseries.lib.AquaticLogger.AbstractAquaticLogger() {
        override fun log(message: String) {
            Bukkit.getConsoleSender().sendMessage("§e[INFO] $message")
        }
    }

    abstract inner class AbstractAquaticLogger() {
        abstract fun log(message: String)
    }

}