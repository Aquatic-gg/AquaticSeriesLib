package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.command.CommandSender
import java.util.function.Consumer

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

    fun replace(placeholders: Placeholders): Message {
        this.messages = placeholders.replace(messages)
        return this
    }

    fun replace(from: String, to: String): Message {
        messages = messages.map { it.replace(from, to) }.toMutableList()
        return this
    }

    fun send(sender: CommandSender) {
        if (messages.size == 1 && messages.first().isEmpty()) {
            return
        }
        messages.forEach(Consumer { v: String ->
            v.toAquatic().send(sender)
        })
    }

    fun broadcast() {
        if (messages.size == 1 && messages.first().isEmpty()) {
            return
        }
        messages.forEach(Consumer { v: String ->
            v.toAquatic().broadcast()
        })
    }

    fun toAquatic(): List<AquaticString> {
        return messages.toAquatic()
    }

}