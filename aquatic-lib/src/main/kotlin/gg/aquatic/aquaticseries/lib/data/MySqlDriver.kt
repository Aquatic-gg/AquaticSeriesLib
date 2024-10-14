package gg.aquatic.aquaticseries.lib.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MySqlDriver(
    ip: String,
    port: Int,
    database: String,
    username: String,
    password: String,
    maxPoolSize: Int = 10,
    poolName: String,
) : DataDriver {

    val config = HikariConfig().apply {
        this.jdbcUrl = "jdbc:mysql://$ip:$port/$database"
        this.username = username
        this.password = password
        this.maximumPoolSize = maxPoolSize
        this.poolName = poolName
    }
    val dataSource = HikariDataSource(config)


    override fun <T> executeQuery(sql: String, preparedStatement: PreparedStatement.() -> Unit, resultSet: ResultSet.() -> T): T {
        return getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                preparedStatement(statement)
                resultSet(statement.executeQuery())
            }
        }
    }

    override fun executeBatch(sql: String, preparedStatement: PreparedStatement.() -> Unit): IntArray {
        getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                preparedStatement(statement)
                return statement.executeBatch()
            }
        }
    }

    override fun execute(sql: String, preparedStatement: PreparedStatement.() -> Unit): Boolean {
        getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                preparedStatement(statement)
                return statement.execute()
            }
        }
    }

    override fun <T> preparedStatement(sql: String, preparedStatement: PreparedStatement.() -> T): T {
        return useConnection {
            prepareStatement(sql).use { statement ->
                preparedStatement(statement)
            }
        }
    }

    override fun <T> useConnection(connection: Connection.() -> T): T {
        getConnection().use {
            return connection(it)
        }
    }

    override fun <T> statement(statement: Statement.() -> T): T {
        return useConnection {
            createStatement().use { s ->
                statement(s)
            }
        }
    }

    fun getConnection(): Connection {
        return dataSource.connection
    }
}