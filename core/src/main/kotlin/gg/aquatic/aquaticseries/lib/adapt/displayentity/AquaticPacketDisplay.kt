package gg.aquatic.aquaticseries.lib.adapt.displayentity

import org.bukkit.entity.Player

abstract class AquaticPacketDisplay {

    abstract fun show(vararg players: Player)
    abstract fun hide(vararg players: Player)

}