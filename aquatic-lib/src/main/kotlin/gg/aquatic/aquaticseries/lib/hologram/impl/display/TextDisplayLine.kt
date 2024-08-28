package gg.aquatic.aquaticseries.lib.hologram.impl.display

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.displayentity.AquaticTextDisplay
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.displayentity.AquaticDisplay
import gg.aquatic.aquaticseries.lib.displayentity.AquaticPacketDisplay
import gg.aquatic.aquaticseries.lib.toAquatic
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.function.Consumer
import gg.aquatic.aquaticseries.lib.hologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class TextDisplayLine(
    override var text: AquaticString,
    override var paddingTop: Double,
    override var paddingBottom: Double,
    val onSpawn: Consumer<AquaticPacketDisplay>,
    ) : AquaticHologram.Line() {

    companion object {
        private val ADAPTER = AbstractAquaticSeriesLib.INSTANCE.displayAdapter!!
    }

    var display: AquaticPacketDisplay? = null

    override fun spawn(location: Location, offset: Vector, placeholders: Placeholders, audience: AquaticAudience) {
        var packetDisplay = display
        if (packetDisplay != null) {
            despawn()
        }
        packetDisplay = ADAPTER.createPacketTextDisplay(location, audience)
        display = packetDisplay
        val textDisplay = packetDisplay.display as AquaticTextDisplay
        textDisplay.setText(placeholders.replace(text.string).toAquatic())
        textDisplay.setTransformation(
            AquaticDisplay.Transformation(
                Vector3f(offset.x.toFloat(), offset.y.toFloat(), offset.z.toFloat()),
                Quaternionf(),
                Vector3f(1f,1f,1f),
                Quaternionf(),
            )
        )
        onSpawn.accept(packetDisplay)
        packetDisplay.update()
    }

    override fun teleport(location: Location, offset: Vector) {
        var d = display ?: return
        val audience = d.packetEntity.audience
        val previousText: AquaticString =
            if (d.packetEntity.bukkitEntity == null) "".toAquatic()
            else AbstractAquaticSeriesLib.INSTANCE.adapter.getEntityName(d.packetEntity.bukkitEntity!!)
        d.display.despawn()
        spawn(location,offset,Placeholders(),audience)
        d = display ?: return
        val textDisplay = d.display as AquaticTextDisplay
        textDisplay.setText(previousText)
    }

    override fun update(placeholders: Placeholders) {
        val d = display ?: return
        val textDisplay = d.display as AquaticTextDisplay
        textDisplay.setText(placeholders.replace(text.string).toAquatic())
    }

    override fun despawn() {
        val d = display ?: return
        d.display.despawn()
        display = null
    }

    override fun show(player: Player) {
        val d = display ?: return
        d.show(player)
    }

    override fun hide(player: Player) {
        val d = display ?: return
        d.hide(player)
    }
}