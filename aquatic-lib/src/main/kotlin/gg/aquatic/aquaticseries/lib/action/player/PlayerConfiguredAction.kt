package gg.aquatic.aquaticseries.lib.action.player

import gg.aquatic.aquaticseries.lib.action.AbstractAction
import gg.aquatic.aquaticseries.lib.action.ConfiguredAction
import org.bukkit.entity.Player

class PlayerConfiguredAction(
    action: AbstractAction<Player>,
    arguments: Map<String, Any?>
) : ConfiguredAction<Player>(action, arguments) {
}