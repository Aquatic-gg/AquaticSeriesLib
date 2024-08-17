package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.action.player.impl.*

class ActionTypes {

    companion object {
        val actions = HashMap<String, AbstractAction<*>>().apply {
            this += "command" to CommandAction()
            this += "message" to MessageAction()
            this += "actionbar" to ActionbarAction()
            this += "title" to TitleAction()
            this += "bossbar" to BossbarAction()
            this += "sound" to SoundAction()
            this += "broadcast" to BroadcastAction()
        }
    }

}