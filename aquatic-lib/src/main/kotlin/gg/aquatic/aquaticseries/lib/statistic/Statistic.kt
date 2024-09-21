package gg.aquatic.aquaticseries.lib.statistic

import gg.aquatic.aquaticseries.lib.data.datatype.DBDataType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.concurrent.TimeUnit

abstract class Statistic<T: Any>(
    val id: String,
    val roundingMode: TimeUnit
) {
    abstract val dataType: DBDataType<T>

    abstract fun createPoint(value: T): StatisticPoint<T>
    abstract fun createEntry(total: T): StatisticEntry<T>

    abstract fun applySqlStatment(preparedStatement: PreparedStatement, value: T)
    abstract fun getSqlValue(resultSet: ResultSet): T

}