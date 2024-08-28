package gg.aquatic.aquaticseries.lib.audience

import org.bukkit.entity.Player
import java.util.UUID

interface AquaticAudience {

    val uuids: Collection<UUID>
    fun canBeApplied(player: Player): Boolean

}