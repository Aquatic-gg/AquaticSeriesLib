package gg.aquatic.aquaticseries.lib.hologram.impl.armorstand

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.lib.audience.AquaticAudience
import gg.aquatic.aquaticseries.lib.fake.PacketEntity
import gg.aquatic.aquaticseries.lib.hologram.AquaticHologram
import gg.aquatic.aquaticseries.lib.replace
import gg.aquatic.aquaticseries.lib.toAquatic
import gg.aquatic.aquaticseries.lib.util.placeholder.Placeholders
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class ArmorstandLine(
    override var text: AquaticString,
    override var paddingTop: Double,
    override var paddingBottom: Double
) : AquaticHologram.Line() {

    var armorStand: PacketEntity? = null

    override fun spawn(location: Location, offset: Vector, placeholders: Placeholders, audience: AquaticAudience) {
        val asId = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.spawnEntity(
            location.clone().add(offset.add(Vector(0.0, -1.75, 0.0))), "ARMORSTAND", audience
        ) {
            it.isInvulnerable = true
            it.isSilent = true
        }
        val aS = PacketEntity(location.clone().add(offset), asId, audience) {}
        armorStand = aS
        text.string.toAquatic().replace(placeholders).setEntityName(aS.bukkitEntity!!)
        aS.update()
    }

    override fun teleport(location: Location, offset: Vector) {
        if (armorStand == null) {
            return
        }
        val previousText = AbstractAquaticSeriesLib.INSTANCE.adapter.getEntityName(armorStand!!.bukkitEntity!!)
        despawn()
        spawn(location,offset,Placeholders(),armorStand!!.audience)
        previousText.setEntityName(armorStand!!.bukkitEntity!!)
        armorStand!!.update()
    }

    override fun update(placeholders: Placeholders) {
        val aS = armorStand ?: return
        placeholders.replace(text.string).toAquatic().setEntityName(aS.bukkitEntity!!)
    }

    override fun despawn() {
        val aS = armorStand ?: return
        aS.despawn()
        armorStand = null
    }

    override fun show(player: Player) {
        val aS = armorStand ?: return
        aS.sendSpawnPacket(player)
    }

    override fun hide(player: Player) {
        val aS = armorStand ?: return
        aS.sendDespawnPacket(player)
    }

}