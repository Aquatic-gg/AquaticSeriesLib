package gg.aquatic.aquaticseries.lib.displayentity.implextended

import gg.aquatic.aquaticseries.lib.AbstractAquaticSeriesLib
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticDisplay
import gg.aquatic.aquaticseries.lib.adapt.displayentity.AquaticTextDisplay
import gg.aquatic.aquaticseries.lib.fake.FakeObjectHandler
import gg.aquatic.aquaticseries.lib.fake.PacketEntity
import gg.aquatic.aquaticseries.lib.util.AudienceList
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.Display
import org.bukkit.entity.Entity
import org.bukkit.entity.TextDisplay
import org.bukkit.util.Transformation

class AquaticTextDisplayImpl(
    packetBased: Boolean,
    location: Location,
    audience: AudienceList): AquaticTextDisplay() {

    var packetEntity: PacketEntity? = null
    var entity: TextDisplay? = null

    init {
        if (packetBased) {
            val id = AbstractAquaticSeriesLib.INSTANCE.nmsAdapter!!.spawnEntity(location,"TEXT_DISPLAY", audience) {}
            packetEntity = PacketEntity(
                location,
                id,
                audience
            ) {}
            packetEntity!!.spawn()
        } else {
            entity = location.world!!.spawn(location, TextDisplay::class.java) {
                it.isPersistent = false
            }
        }
    }

    override fun despawn() {
        if (isPacket) {
            FakeObjectHandler.unregisterEntity(packetEntity!!.location,packetEntity!!.entityId)
            packetEntity!!.despawn()
        }
        getEntity().remove()
    }

    override val isPacket: Boolean = packetBased

    override fun getEntity(): Entity {
        if (isPacket) {
            return packetEntity!!.bukkitEntity!!
        }
        return entity!!
    }

    override fun getTransformation(): AquaticDisplay.Transformation {
        val transformation = (getEntity() as TextDisplay).transformation
        return AquaticDisplay.Transformation(
            transformation.translation,
            transformation.leftRotation,
            transformation.scale,
            transformation.rightRotation
        )
    }

    override fun setTransformation(var1: AquaticDisplay.Transformation) {
        val transformation = Transformation(
            var1.translation,
            var1.leftRotation,
            var1.scale,
            var1.rightRotation)
        (getEntity() as Display).transformation = transformation
    }

    override fun getInterpolationDuration(): Int {
        return (getEntity() as Display).interpolationDuration
    }

    override fun setInterpolationDuration(var1: Int) {
        (getEntity() as Display).interpolationDuration = var1
    }

    override fun getTeleportDuration(): Int {
        return (getEntity() as Display).teleportDuration
    }

    override fun setTeleportDuration(var1: Int) {
        (getEntity() as Display).teleportDuration = var1
    }

    override fun getViewRange(): Float {
        return (getEntity() as Display).viewRange
    }

    override fun setViewRange(var1: Float) {
        (getEntity() as Display).viewRange = var1
    }

    override fun getShadowRadius(): Float {
       return (getEntity() as Display).shadowRadius
    }

    override fun setShadowRadius(var1: Float) {
        (getEntity() as Display).shadowRadius = var1
    }

    override fun getShadowStrength(): Float {
        return (getEntity() as Display).shadowStrength
    }

    override fun setShadowStrength(var1: Float) {
        (getEntity() as Display).shadowStrength = var1
    }

    override fun getDisplayWidth(): Float {
        return (getEntity() as Display).displayWidth
    }

    override fun setDisplayWidth(var1: Float) {
        (getEntity() as Display).displayWidth = var1
    }

    override fun getDisplayHeight(): Float {
        return (getEntity() as Display).displayHeight
    }

    override fun setDisplayHeight(var1: Float) {
        (getEntity() as Display).displayHeight = var1
    }

    override fun getInterpolationDelay(): Int {
        return (getEntity() as Display).interpolationDelay
    }

    override fun setInterpolationDelay(var1: Int) {
        (getEntity() as Display).interpolationDelay = var1
    }

    override fun getBillboard(): AquaticDisplay.Billboard {
        val billboard = (getEntity() as Display).billboard
        return AquaticDisplay.Billboard.valueOf(billboard.toString().uppercase())
    }

    override fun setBillboard(billboard: AquaticDisplay.Billboard) {
        val billboardType = Display.Billboard.valueOf(billboard.toString().uppercase())
        (getEntity() as Display).billboard = billboardType
    }

    override fun getGlowColorOverride(): Color? {
        return (getEntity() as Display).glowColorOverride
    }

    override fun setGlowColorOverride(var1: Color?) {
        (getEntity() as Display).glowColorOverride = var1
    }

    override fun getBrightness(): AquaticDisplay.Brigthness? {
        val brightness = (getEntity() as Display).brightness ?: return null
        return AquaticDisplay.Brigthness(
            brightness.blockLight,
            brightness.skyLight
        )
    }

    override fun setBrightness(brightness: AquaticDisplay.Brigthness) {
        val brightness2 = Display.Brightness(brightness.blockLight,brightness.skyLight)
        (getEntity() as Display).brightness = brightness2
    }
}