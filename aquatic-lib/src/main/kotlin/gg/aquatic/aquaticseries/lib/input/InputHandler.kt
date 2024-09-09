package gg.aquatic.aquaticseries.lib.input

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.feature.Features
import gg.aquatic.aquaticseries.lib.feature.IFeature
import gg.aquatic.aquaticseries.lib.input.type.chat.ChatInputHandler
import gg.aquatic.aquaticseries.lib.register
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object InputHandler : IFeature, IHandler {
    override val type: Features = Features.PLAYER_INPUT

    val handlers = listOf(
        ChatInputHandler
    )

    override fun initialize(lib: AbstractAquaticSeriesLib) {
        Listeners().register()
    }

    override fun isBeingAwaited(player: Player): Boolean {
        for (handler in handlers) {
            if (handler.isBeingAwaited(player)) return true
        }
        return false
    }

    class Listeners : Listener {

        @EventHandler
        fun AsyncPlayerChatEvent.onChat() {
            ChatInputHandler.onChatEvent(this)
        }
    }
}