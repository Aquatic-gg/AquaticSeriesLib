package gg.aquatic.aquaticseries.lib.data.datatype

import java.sql.ResultSet

interface DBDataType<T> {

    val dataClass: Class<T>
    fun setPStatementValue(index: Int, value: T, statement: java.sql.PreparedStatement)
    fun getResultSetValue(index: Int, resultSet: ResultSet): T

}