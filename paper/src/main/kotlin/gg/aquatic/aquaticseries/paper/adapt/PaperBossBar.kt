package gg.aquatic.aquaticseries.paper.adapt

import gg.aquatic.aquaticseries.lib.adapt.AquaticBossBar
import gg.aquatic.aquaticseries.lib.adapt.AquaticString
import gg.aquatic.aquaticseries.paper.PaperAdapter
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player

class PaperBossBar(
    private var _text: AquaticString,
    private var _color: Color,
    private var _style: Style,
    private var _progress: Double
) :
    AquaticBossBar() {

    val bossBar = BossBar.bossBar(
        PaperAdapter.minimessage.deserialize(text.string),
        progress.toFloat(),
        BossBar.Color.valueOf(color.name.uppercase()),
        BossBar.Overlay.entries[style.ordinal]
    )
    override var text: AquaticString
        get() = _text
        set(value) {
            this._text = value
            this.bossBar.name(convert(value))
        }

    override var color: Color
        get() = _color
        set(value) {
            this._color = value
            this.bossBar.color(BossBar.Color.valueOf(value.name.uppercase()))
        }

    override var style: Style
        get() = _style
        set(value) {
            this._style = value
            this.bossBar.overlay(BossBar.Overlay.entries[value.ordinal])
        }

    override var progress: Double
        get() = _progress
        set(value) {
            this._progress = value
            this.bossBar.progress(value.toFloat())
        }

    override fun addPlayer(player: Player) {
        bossBar.addViewer(player)
    }

    override fun removePlayer(player: Player) {
        bossBar.removeViewer(player)
    }
    private fun convert(aquaticString: AquaticString): Component {
        val legacyComp = LegacyComponentSerializer.legacy('§').deserialize(ChatColor.translateAlternateColorCodes('&', aquaticString.string))
        val preparedString = LegacyComponentSerializer.legacy('§').serialize(legacyComp)
        return PaperAdapter.minimessage.deserialize(preparedString)
    }

}