package gg.aquatic.aquaticseries.lib.hologram

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.BlacklistAudience
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class AquaticHologram(
    location: Location,
    placeholders: Placeholders,
    val audience: AquaticAudience,
    val lines: List<Line>
) {
    var spawned = true


    private var _location: Location = location.clone()
    var location: Location
        get() = _location.clone()
        set(value) {
            handleTeleport(value)
            _location = value.clone()
        }

    init {
        spawn(location, placeholders)
        updateAudienceView()
    }

    fun show(player: Player) {
        if (!spawned) return
        if (handleAudienceShow(player)) return
        for (line in lines) {
            line.show(
                player
            )
        }
    }

    fun hide(player: Player) {
        if (!spawned) return
        if (handleAudienceHide(player)) return
        for (line in lines) {
            line.hide(
                player
            )
        }
    }

    fun updateAudienceView() {
        if (!spawned) return
        /*
        for (player in location.world!!.players) {
            for (line in lines) {
                line.hide(
                    player
                )
            }
        }
         */
        for (player in location.world!!.players.filter { audience.canBeApplied(it) }) {
            for (line in lines) {
                line.show(
                    player
                )
            }
        }
    }

    private fun handleAudienceShow(player: Player): Boolean {
        val au = audience
        if (au is WhitelistAudience) {
            if (au.contains(player)) return true
            au.add(player)
        } else if (au is BlacklistAudience) {
            if (!au.contains(player)) return true
            au.remove(player)
        }
        return false
    }

    private fun handleAudienceHide(player: Player): Boolean {
        val au = audience
        if (au is WhitelistAudience) {
            if (!au.contains(player)) return true
            au.remove(player)
        } else if (au is BlacklistAudience) {
            if (au.contains(player)) return true
            au.add(player)
        }
        return false
    }

    private fun spawn(location: Location, placeholders: Placeholders) {
        var currentHeight = 0.0
        for ((index, line) in lines.reversed().withIndex()) {
            currentHeight += line.paddingBottom
            line.spawn(location, Vector(0.0, currentHeight, 0.0), placeholders, audience)
            currentHeight += line.paddingTop + line.height
        }
    }

    fun despawn() {
        if (!spawned) return
        for (line in lines) {
            line.despawn()
        }
        spawned = false
    }

    fun handleTeleport(location: Location) {
        if (!spawned) return
        var currentHeight = 0.0
        for ((index, line) in lines.reversed().withIndex()) {
            currentHeight += line.paddingBottom
            line.teleport(location, Vector(0.0, currentHeight, 0.0))
            currentHeight += line.paddingTop + line.height
        }
    }

    abstract class Line {

        abstract var text: AquaticString
        abstract fun spawn(location: Location, offset: Vector, placeholders: Placeholders, audience: AquaticAudience)
        abstract fun teleport(location: Location, offset: Vector)
        abstract fun update(placeholders: Placeholders)
        abstract fun despawn()
        abstract var paddingTop: Double
        abstract var paddingBottom: Double

        abstract fun show(player: Player)
        abstract fun hide(player: Player)

        var height: Double = 0.3

    }

}