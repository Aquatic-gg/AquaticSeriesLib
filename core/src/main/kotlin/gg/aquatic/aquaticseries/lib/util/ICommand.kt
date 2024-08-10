package gg.aquatic.aquaticseries.lib.util

import org.bukkit.command.CommandSender

interface ICommand {

    fun run(sender: CommandSender, args: Array<out String>)

}