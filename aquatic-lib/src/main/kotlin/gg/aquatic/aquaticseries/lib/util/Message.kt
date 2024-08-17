package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.command.CommandSender

class Message {

    var messages: MutableList<String>

    constructor(message: String?) {
        messages = if (message != null) {
            arrayListOf(message)
        } else {
            ArrayList()
        }
    }

    constructor(messages: Collection<String>) {
        this.messages = messages.toMutableList()
    }

    fun replacePlaceholders(placeholders: Placeholders): Message {
        this.messages = placeholders.replace(messages)
        return this
    }

    fun send(sender: CommandSender) {
        for (message in messages) {
            sender.sendMessage(message.toComponent())
        }
    }

}