package gg.aquatic.aquaticseries.lib.util

import org.bukkit.Bukkit
import java.util.UUID

class AudienceList(
    val whitelist: MutableList<UUID>,
    val mode: Mode,
) {

    enum class Mode {
        WHITELIST,
        BLACKLIST,
    }

    fun canBeApplied(uuid: UUID): Boolean {
        return when (mode) {
            Mode.WHITELIST -> whitelist.contains(uuid)
            Mode.BLACKLIST -> !whitelist.contains(uuid)
        }
    }

    val appliedTo: List<UUID>
        get() {
            return if (mode == Mode.WHITELIST) {
                return whitelist
            } else {
                Bukkit.getOnlinePlayers().filter { !whitelist.contains(it.uniqueId) }.map { it.uniqueId }
            }
        }

}