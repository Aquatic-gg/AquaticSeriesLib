package gg.aquatic.aquaticseries.lib.data

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

interface DataDriver {
    fun <T> executeQuery(sql: String, preparedStatement: PreparedStatement.() -> Unit, resultSet: ResultSet.() -> T): T
    fun executeBatch(sql: String, preparedStatement: PreparedStatement.() -> Unit): IntArray
    fun execute(sql: String, preparedStatement: PreparedStatement.() -> Unit): Boolean
    fun <T> preparedStatement(sql: String, preparedStatement: PreparedStatement.() -> T): T
    fun <T> useConnection(connection: Connection.() -> T): T
    fun <T> statement(statement: Statement.() -> T): T
}