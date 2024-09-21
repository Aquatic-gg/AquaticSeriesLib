package gg.aquatic.aquaticseries.lib.data.datatype

import java.sql.PreparedStatement
import java.sql.ResultSet

class IntDataType: DBDataType<Int> {
    override val dataClass: Class<Int> = Int::class.java
    override fun getResultSetValue(index: Int, resultSet: ResultSet): Int {
        return resultSet.getInt(index)
    }

    override fun setPStatementValue(index: Int, value: Int, statement: PreparedStatement) {
        statement.setInt(index, value)
    }
}