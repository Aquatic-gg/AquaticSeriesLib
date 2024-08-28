package gg.aquatic.aquaticseries.lib.audience

import java.util.*

class WhitelistAudience(
    val whitelist: MutableList<UUID>
): AquaticAudience {
    override val uuids: Collection<UUID>
        get() {
            return whitelist
        }
}