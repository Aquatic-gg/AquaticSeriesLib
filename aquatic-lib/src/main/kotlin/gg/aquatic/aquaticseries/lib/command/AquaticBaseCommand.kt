package gg.aquatic.aquaticseries.lib.command

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.util.ICommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class AquaticBaseCommand(
    name: String,
    description: String,
    aliases: MutableList<String>,
    val subCommands: MutableMap<String,ICommand>,
    val helpMessage: List<AquaticString>
) : Command(name, description, "/$name", aliases) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            helpMessage.forEach { it.send(sender) }
            return true
        }

        val cmd = subCommands[args[0]]
        if (cmd == null) {
            helpMessage.forEach { it.send(sender) }
            return true
        }
        cmd.run(sender, args)
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        if (args.size < 2) {
            return subCommands.keys.toList()
        }

        val cmd = subCommands[args[0]] ?: return listOf()
        return cmd.tabComplete(sender, args.drop(1).toTypedArray())
    }

    fun registerCmd(namespace: String) {
        register(namespace)
    }
}