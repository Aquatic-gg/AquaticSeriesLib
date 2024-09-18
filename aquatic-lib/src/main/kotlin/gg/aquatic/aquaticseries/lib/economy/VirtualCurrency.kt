package gg.aquatic.aquaticseries.lib.economy

import java.util.UUID

interface VirtualCurrency: Currency {

    fun giveOffline(player: UUID, amount: Double)
    fun takeOffline(player: UUID, amount: Double)
    fun getBalanceOffline(player: UUID): Double
    fun hasOffline(player: UUID, amount: Double): Boolean

}