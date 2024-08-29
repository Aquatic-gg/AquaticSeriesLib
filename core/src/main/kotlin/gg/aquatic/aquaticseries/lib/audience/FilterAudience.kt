package gg.aquatic.aquaticseries.lib.audience

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.function.Function

class FilterAudience(
    val filter: Function<Player,Boolean>
): AquaticAudience {
    override val uuids: Collection<UUID>
        get() {
            return Bukkit.getOnlinePlayers().filter { p -> filter.apply(p) }.map { it.uniqueId }
        }

    override fun canBeApplied(player: Player): Boolean {
        return filter.apply(player)
    }

}