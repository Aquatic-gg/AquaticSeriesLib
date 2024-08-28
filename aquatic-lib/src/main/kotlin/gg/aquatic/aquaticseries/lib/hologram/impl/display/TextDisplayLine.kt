package gg.aquatic.aquaticseries.lib.hologram.impl.display

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.audience.GlobalAudience
import gg.aquatic.aquaticseries.lib.audience.WhitelistAudience
import gg.aquatic.aquaticseries.lib.toAquatic
import org.joml.Quaternionf
import org.joml.Vector3f
import java.util.function.Consumer
import gg.aquatic.aquaticseries.lib.hologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Display
import org.bukkit.entity.Player
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation
import org.bukkit.util.Vector

class TextDisplayLine(
    override var text: AquaticString,
    override var paddingTop: Double,
    override var paddingBottom: Double,
    val onSpawn: Consumer<TextDisplay>,
) : AquaticHologram.Line() {

    var entityId: Int? = null
    var anchorOffset: Vector? = null

    override fun spawn(location: Location, offset: Vector, placeholders: Placeholders, audience: AquaticAudience) {
        if (entityId != null) {
            despawn()
        }
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        entityId = nmsAdapter.spawnEntity(location, "text_display", audience) { e ->
            val textDisplay = e as TextDisplay
            //textDisplay.isCustomNameVisible = true
            //textDisplay.customName = placeholders.replace(text.string)

            textDisplay.text = placeholders.replace(text.string)
            textDisplay.transformation = Transformation(
                Vector3f(
                    offset.x.toFloat() + (anchorOffset?.x?.toFloat() ?: 0f),
                    offset.y.toFloat() + (anchorOffset?.y?.toFloat() ?: 0f),
                    offset.z.toFloat() + (anchorOffset?.z?.toFloat() ?: 0f)
                ),
                Quaternionf(),
                Vector3f(1f, 1f, 1f),
                Quaternionf(),
            )
            textDisplay.billboard = Display.Billboard.CENTER
            onSpawn.accept(textDisplay)
        }
        for (uuid in audience.uuids) {
            Bukkit.broadcastMessage("Showing line to $uuid")
        }
    }

    override fun teleport(location: Location, offset: Vector) {
        var id = entityId ?: return
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        val audience = GlobalAudience()
        var textDisplay = nmsAdapter.getEntity(id) as TextDisplay
        val previousText: AquaticString = textDisplay.text?.toAquatic() ?: return
        textDisplay.remove()
        nmsAdapter.despawnEntity(listOf(id), audience)
        spawn(location, offset, Placeholders(), audience)
        id = entityId ?: return
        textDisplay = nmsAdapter.getEntity(id) as TextDisplay
        nmsAdapter.updateEntity(id, { e ->
            textDisplay.text = previousText.string
        }, audience)
    }

    override fun update(placeholders: Placeholders) {
        val id = entityId ?: return
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        nmsAdapter.updateEntity(id, { e ->
            val textDisplay = e as ArmorStand
            textDisplay.customName = placeholders.replace(text.string)
        }, GlobalAudience())
    }

    override fun despawn() {
        val id = entityId ?: return
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        val textDisplay = nmsAdapter.getEntity(id) as TextDisplay
        textDisplay.remove()
        nmsAdapter.despawnEntity(listOf(id), GlobalAudience())
        entityId = null
    }

    override fun show(player: Player) {
        val id = entityId ?: return
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        nmsAdapter.resendEntitySpawnPacket(player, id)

        Bukkit.broadcastMessage("Showing line to ${player.name}")
    }

    override fun hide(player: Player) {
        val id = entityId ?: return
        val nmsAdapter = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!
        nmsAdapter.despawnEntity(listOf(id), WhitelistAudience(mutableListOf(player.uniqueId)))
    }
}