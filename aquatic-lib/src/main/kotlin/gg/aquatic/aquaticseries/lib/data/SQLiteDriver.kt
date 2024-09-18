package gg.aquatic.aquaticseries.lib.data

import java.io.File
import java.sql.*

class SQLiteDriver(
    val databaseFile: File
): DataDriver {

    private var _activeConnection: Connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.path)
    val activeConnection: Connection
        get() {
            try {
                if (!activeConnection.isClosed) return this._activeConnection
                Class.forName("org.sqlite.SQLiteDataSource")
                this._activeConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.path)
            } catch (e: SQLException) {
                throw RuntimeException(e)
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
            return this._activeConnection
        }

    override fun executeQuery(sql: String, preparedStatement: PreparedStatement.() -> Unit): ResultSet {
        activeConnection.prepareStatement(sql).use { statement ->
            preparedStatement(statement)
            return statement.executeQuery()
        }
    }

    override fun executeBatch(sql: String, preparedStatement: PreparedStatement.() -> Unit): IntArray {
        activeConnection.prepareStatement(sql).use { statement ->
            preparedStatement(statement)
            return statement.executeBatch()
        }
    }

    override fun execute(sql: String, preparedStatement: PreparedStatement.() -> Unit): Boolean {
        activeConnection.prepareStatement(sql).use { statement ->
            preparedStatement(statement)
            return statement.execute()
        }
    }

    override fun preparedStatement(sql: String, preparedStatement: PreparedStatement.() -> Unit) {
        activeConnection.prepareStatement(sql).use { statement ->
            preparedStatement(statement)
        }
    }
}