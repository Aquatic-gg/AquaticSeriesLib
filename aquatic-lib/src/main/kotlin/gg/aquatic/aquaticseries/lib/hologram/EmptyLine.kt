package gg.aquatic.aquaticseries.lib.hologram

import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class EmptyLine(
    override var paddingTop: Double, override var paddingBottom: Double
): AquaticHologram.Line() {

    override var text: AquaticString
        get() = "".toAquatic()
        set(value) {}

    override fun spawn(location: Location, offset: Vector, placeholders: Placeholders, audience: AquaticAudience) {

    }

    override fun teleport(location: Location, offset: Vector) {

    }

    override fun update(placeholders: Placeholders) {

    }

    override fun despawn() {
    }

    override fun show(player: Player) {
    }

    override fun hide(player: Player) {
    }
}