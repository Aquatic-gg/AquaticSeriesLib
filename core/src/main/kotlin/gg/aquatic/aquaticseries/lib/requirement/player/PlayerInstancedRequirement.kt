package gg.aquatic.aquaticseries.lib.requirement.player

import org.bukkit.entity.Player
import gg.aquatic.aquaticseries.lib.requirement.AbstractInstancedRequirement

class PlayerInstancedRequirement(
    requirement: PlayerRequirement,
    arguments: Map<String,Any?>
): AbstractInstancedRequirement<Player>(requirement, arguments) {
}