package gg.aquatic.aquaticseries.spigot.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player

class SpigotBossBar(
    private var _text: AquaticString,
    private var _color: Color,
    private var _style: Style,
    private var _progress: Double
) : AquaticBossBar() {

    val bossBar = Bukkit.createBossBar((text as? SpigotString)?.formatted ?: text.string, BarColor.valueOf(color.name.uppercase()), BarStyle.valueOf(style.name.uppercase())).apply {
        this.progress = _progress
    }

    override var text: AquaticString
        get() = _text
        set(value) {
            this._text = value
            this.bossBar.setTitle((value as? SpigotString)?.formatted ?: value.string)
        }

    override var color: Color
        get() = _color
        set(value) {
            this._color = value
            this.bossBar.color = BarColor.valueOf(value.name.uppercase())
        }

    override var style: Style
        get() = _style
        set(value) {
            this._style = value
            this.bossBar.style = BarStyle.valueOf(value.name.uppercase())
        }

    override var progress: Double
        get() = _progress
        set(value) {
            this._progress = value
            this.bossBar.progress = value
        }

    override fun addPlayer(player: Player) {
        bossBar.addPlayer(player)
    }

    override fun removePlayer(player: Player) {
        bossBar.removePlayer(player)
    }
}