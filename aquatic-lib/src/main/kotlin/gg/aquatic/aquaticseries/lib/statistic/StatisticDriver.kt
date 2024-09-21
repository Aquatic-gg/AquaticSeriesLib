package gg.aquatic.aquaticseries.lib.statistic

import gg.aquatic.aquaticseries.lib.data.DataDriver
import gg.aquatic.aquaticseries.lib.util.toBytes
import gg.aquatic.aquaticseries.lib.util.toUUID
import java.util.*
import java.util.concurrent.CompletableFuture

class StatisticDriver(
    val driver: DataDriver
) {

    /*
    fun get(uuid: UUID): CompletableFuture<StatisticPlayer> {
        val future = CompletableFuture<StatisticPlayer>()
        CompletableFuture.runAsync {
            val rs = driver.executeQuery("SELECT * FROM aquaticcurrency WHERE uuid = ?") {
                setBytes(1, uuid.toBytes())
            }

            val economyPlayer = StatisticPlayer(uuid)
            while(rs.next()) {
                val currencyId = rs.getString("currency_id")
                val balance = rs.getDouble("balance")
                economyPlayer.balance[currencyId] = balance
            }
            future.complete(economyPlayer)
        }
        return future
    }

    fun get(uuid: UUID, statistic: Statistic<*>): CompletableFuture<Double> {
        val future = CompletableFuture<Double>()
        CompletableFuture.runAsync {
            val rs = driver.executeQuery("SELECT balance FROM aquaticcurrency WHERE uuid = ? AND currency_id = ?") {
                setBytes(1, uuid.toBytes())
                statistic.applySqlStatment(this)
            }
            if (rs.next()) {
                future.complete(rs.getDouble("balance"))
            } else {
                future.complete(0.0)
            }
        }
        return future
    }
    fun getAll(): CompletableFuture<Map<UUID, StatisticPlayer>> {
        val future = CompletableFuture<Map<UUID, StatisticPlayer>>()
        CompletableFuture.runAsync {
            val rs = driver.executeQuery("SELECT * FROM aquaticcurrency") {}
            val map = HashMap<UUID, StatisticPlayer>()
            while(rs.next()) {
                val uuid = rs.getBytes("uuid").toUUID()
                val currencyId = rs.getString("currency_id")
                val balance = rs.getDouble("balance")

                val player = map.getOrPut(uuid) { StatisticPlayer(uuid) }
                player.balance[currencyId] = balance
            }
            future.complete(map)
        }
        return future
    }

    fun getAll(players: Set<UUID>): CompletableFuture<Map<UUID, StatisticPlayer>> {
        val future = CompletableFuture<Map<UUID, StatisticPlayer>>()
        CompletableFuture.runAsync {
            val rs = driver.executeQuery("SELECT * FROM aquaticcurrency WHERE uuid in (${players.joinToString { "?" }})") {
                players.forEachIndexed { index, uuid ->
                    setString(index + 1, uuid.toString())
                }
            }
            val map = HashMap<UUID, StatisticPlayer>()
            while(rs.next()) {
                val uuid = rs.getBytes("uuid").toUUID()
                val currencyId = rs.getString("currency_id")
                val balance = rs.getDouble("balance")
                val player = map.getOrPut(uuid) { EconomyPlayer(uuid) }
                player.balance[currencyId] = balance
            }
            future.complete(map)
        }
        return future
    }

    fun set(uuid: UUID, currency: Statistic<*>, amount: Double): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            driver.execute("replace into aquaticcurrency values (?, ?, ?)") {
                setBytes(1, uuid.toBytes())
                setString(2, currency.id)
                setDouble(3, amount)
            }
        }
    }

    fun set(vararg economyPlayers: StatisticPlayer): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            driver.executeBatch("replace into aquaticcurrency values (?, ?, ?)") {
                for (economyPlayer in economyPlayers) {
                    for ((id, balance) in economyPlayer.balance) {
                        setBytes(1, economyPlayer.uuid.toBytes())
                        setString(2, id)
                        setDouble(3, balance)
                        addBatch()
                    }
                }
            }
        }
    }

    fun initialize(): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            driver.execute("" +
                    "CREATE TABLE " +
                    "aquaticstatistic (" +
                    "uuid BINARY(16) NOT NULL," +
                    "currency_id NVARCHAR(64) NOT NULL," +
                    "balance DECIMAL NOT NULL," +
                    "PRIMARY KEY (uuid, currency_id)" +
                    ")"
            ) {
            }
        }
    }
     */
}