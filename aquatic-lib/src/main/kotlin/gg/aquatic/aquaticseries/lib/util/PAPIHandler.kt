package gg.aquatic.aquaticseries.lib.util

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

object PAPIHandler {

    fun registerExtension(author: String, identifier: String, onRequest: (player: OfflinePlayer, params: String) -> String) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return
        }
        val extension = object : PlaceholderExpansion() {
            override fun getIdentifier(): String {
                return identifier
            }

            override fun getAuthor(): String {
                return author
            }

            override fun getVersion(): String {
                return "1.0.0"
            }

            override fun canRegister(): Boolean {
                return true
            }

            override fun persist(): Boolean {
                return false
            }

            override fun onRequest(player: OfflinePlayer, params: String): String {
                return onRequest(player, params)
            }
        }
        extension.register()
    }
}