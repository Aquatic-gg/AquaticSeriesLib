package gg.aquatic.aquaticseries.lib.action.player.impl

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.util.argument.AquaticObjectArgument
import gg.aquatic.aquaticseries.lib.util.argument.impl.PrimitiveObjectArgument
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.entity.Player
import java.util.function.BiFunction

class SoundAction: AbstractAction<Player>() {
    override fun run(player: Player, args: Map<String, Any?>, textUpdater: BiFunction<Player, String, String>) {
        val sound = args["sound"] as String
        val volume = args["volume"] as Float
        val pitch = args["pitch"] as Float

        player.playSound(player.location, sound, volume, pitch)
    }

    override fun arguments(): List<AquaticObjectArgument<*>> {
        return listOf(
            PrimitiveObjectArgument("sound", "minecraft:ambient.basalt_deltas.additions", true),
            PrimitiveObjectArgument("volume", 1.0f, false),
            PrimitiveObjectArgument("pitch", 1.0f, false)
        )
    }
}