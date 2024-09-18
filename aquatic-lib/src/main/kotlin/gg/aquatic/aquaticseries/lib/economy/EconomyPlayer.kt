package gg.aquatic.aquaticseries.lib.economy

import java.util.UUID

class EconomyPlayer(
    val uuid: UUID
) {
    val balance = HashMap<String,Double>()
}