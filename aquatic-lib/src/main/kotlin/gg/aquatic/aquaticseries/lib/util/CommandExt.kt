package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.SimpleCommandMap
import java.lang.reflect.Field

fun Command.register(namespace: String) {
    Bukkit.getServer().commandMap().clearCommands()
    Bukkit.getServer().commandMap().register(namespace, this)
}

fun Command.unregister() {
    val commandMap = Bukkit.getServer().commandMap()
    val knownCommands = commandMap.knownCommands()
    knownCommands.remove(name)
    for (alias in this.aliases) {
        if (knownCommands.containsKey(alias) && knownCommands[alias]!!.name.contains(this.name))
            knownCommands.remove(alias)
    }
}

private val bukkitCommandMapField: Field = Bukkit.getServer().javaClass.getDeclaredField("commandMap").apply { isAccessible = true }
//private val knownCommandsField = CommandMap::class.java.getDeclaredField("knownCommands").apply { isAccessible = true }

fun Server.commandMap(): CommandMap {
    return bukkitCommandMapField.get(this) as CommandMap
}

fun CommandMap.knownCommands(): HashMap<String, Command> {
    val commandMap = this as SimpleCommandMap
    return commandMap.knownCommands()
}