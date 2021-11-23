package com.obolonnyy.chartapplication.chart.data

import org.joda.time.LocalDateTime
import java.io.Serializable


class ChartInputData(
    val dateTime: LocalDateTime,
    val value: Double,
    val index: Int,
    val descriptionFirstValue: String? = null,
    val descriptionSecondValue: String? = null,
) : Serializable {

    val hasFirstValue: Boolean get() = descriptionFirstValue != null
    val hasSecondValue: Boolean get() = descriptionSecondValue != null
    val hasValue: Boolean get() = hasFirstValue || hasSecondValue

    override fun equals(other: Any?): Boolean {
        return (other as? ChartInputData)?.value == value &&
                dateTime.equals((other as? ChartInputData)?.dateTime)
                && other.index == index
    }

    override fun hashCode(): Int {
        return dateTime.hashCode()
    }
}