package gg.aquatic.aquaticseries.lib.audience

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class FilterAudience(
    val filter: (Player) -> Boolean
): AquaticAudience {
    override val uuids: Collection<UUID>
        get() {
            return Bukkit.getOnlinePlayers().filter(filter).map { it.uniqueId }
        }
}