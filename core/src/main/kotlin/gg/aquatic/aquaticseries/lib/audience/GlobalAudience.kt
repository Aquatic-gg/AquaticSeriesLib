package gg.aquatic.aquaticseries.lib.audience

import org.bukkit.Bukkit
import java.util.*

class GlobalAudience: AquaticAudience {
    override val uuids: Collection<UUID>
        get() {
            return Bukkit.getOnlinePlayers().map { it.uniqueId }
        }
}