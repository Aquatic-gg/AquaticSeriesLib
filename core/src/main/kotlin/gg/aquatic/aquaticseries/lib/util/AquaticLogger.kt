package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit

class AquaticLogger {


    private inner class ErrorLogger: AbstractAquaticLogger() {
        override fun log(message: String) {
            Bukkit.getConsoleSender().sendMessage("§c[ERROR] $message")
        }
    }

    private inner class InfoLogger: AbstractAquaticLogger() {
        override fun log(message: String) {
            Bukkit.getConsoleSender().sendMessage("§e[INFO] $message")
        }
    }

    abstract inner class AbstractAquaticLogger() {
        abstract fun log(message: String)
    }

}