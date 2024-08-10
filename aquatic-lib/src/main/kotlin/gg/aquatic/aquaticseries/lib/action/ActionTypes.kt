package gg.aquatic.aquaticseries.lib.action

import gg.aquatic.aquaticseries.lib.action.impl.CommandAction
import gg.aquatic.aquaticseries.lib.action.impl.MessageAction

class ActionTypes {

    companion object {
        val actions = HashMap<String, AbstractAction>().apply {
            this += "command" to CommandAction()
            this += "message" to MessageAction()
        }
    }

}