package gg.aquatic.aquaticseries.lib.data.datatype

import java.sql.PreparedStatement
import java.sql.ResultSet

class DoubleDataType: DBDataType<Double> {
    override val dataClass: Class<Double> = Double::class.java

    override fun getResultSetValue(index: Int, resultSet: ResultSet): Double {
        return resultSet.getDouble(index)
    }

    override fun setPStatementValue(index: Int, value: Double, statement: PreparedStatement) {
        statement.setDouble(index, value)
    }
}