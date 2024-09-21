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


    override fun executeQuery(sql: String, preparedStatement: PreparedStatement.() -> Unit): ResultSet {
        getConnection().use { connection ->
            connection.prepareStatement(sql).use { statement ->
                preparedStatement(statement)
                return statement.executeQuery()
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

    override fun preparedStatement(sql: String, preparedStatement: PreparedStatement.() -> Unit) {
        useConnection {
            prepareStatement(sql).use { statement ->
                preparedStatement(statement)
            }
        }
    }

    override fun useConnection(connection: Connection.() -> Unit) {
        getConnection().use {
            connection(it)
        }
    }

    override fun statement(statement: Statement.() -> Unit) {
        useConnection {
            createStatement().use { s ->
                statement(s)
            }
        }
    }

    fun getConnection(): Connection {
        return dataSource.connection
    }
}