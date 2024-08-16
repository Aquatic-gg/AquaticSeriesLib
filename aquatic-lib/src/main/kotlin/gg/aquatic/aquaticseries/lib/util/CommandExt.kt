package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import java.lang.reflect.Field

fun Command.register(namespace: String) {
    try {
        val bukkitCommandMap: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap")

        bukkitCommandMap.setAccessible(true)
        val commandMap: CommandMap = bukkitCommandMap.get(Bukkit.getServer()) as CommandMap

        commandMap.register(namespace, this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}