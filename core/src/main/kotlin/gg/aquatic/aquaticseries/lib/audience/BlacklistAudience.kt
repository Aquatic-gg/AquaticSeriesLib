package gg.aquatic.aquaticseries.lib.audience

import org.bukkit.Bukkit
import java.util.*

class BlacklistAudience(
    val blacklist: MutableList<UUID>
): AquaticAudience {
    override val uuids: Collection<UUID>
        get() {
            return Bukkit.getOnlinePlayers().map { it.uniqueId }.filter { !blacklist.contains(it) }
        }
}