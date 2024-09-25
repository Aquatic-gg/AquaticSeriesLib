package gg.aquatic.aquaticseries.lib.data

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

interface DataDriver {
    fun executeQuery(sql: String, preparedStatement: PreparedStatement.() -> Unit, resultSet: ResultSet.() -> Unit)
    fun executeBatch(sql: String, preparedStatement: PreparedStatement.() -> Unit): IntArray
    fun execute(sql: String, preparedStatement: PreparedStatement.() -> Unit): Boolean
    fun preparedStatement(sql: String, preparedStatement: PreparedStatement.() -> Unit)
    fun useConnection(connection: Connection.() -> Unit)
    fun statement(statement: Statement.() -> Unit)
}