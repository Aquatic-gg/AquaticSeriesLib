package gg.aquatic.aquaticseries.lib.statistic.type.doublestat

import gg.aquatic.aquaticseries.lib.data.datatype.DBDataType
import gg.aquatic.aquaticseries.lib.data.datatype.DoubleDataType
import gg.aquatic.aquaticseries.lib.statistic.Statistic
import gg.aquatic.aquaticseries.lib.statistic.StatisticEntry
import gg.aquatic.aquaticseries.lib.statistic.StatisticPoint
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.concurrent.TimeUnit

class DoubleStatistic(id: String, roundingMode: TimeUnit) : Statistic<Double>(id, roundingMode) {
    override fun createPoint(value: Double): StatisticPoint<Double> {
        return StatisticPoint(System.currentTimeMillis(), value)
    }

    override fun createEntry(total: Double): StatisticEntry<Double> {
        return DoubleStatisticEntry(this, total, roundingMode)
    }

    override val dataType: DBDataType<Double> = DoubleDataType()

    override fun applySqlStatment(preparedStatement: PreparedStatement, value: Double) {
        preparedStatement.setDouble(2, value)
    }

    override fun getSqlValue(resultSet: ResultSet): Double {
        return resultSet.getDouble(2)
    }
}