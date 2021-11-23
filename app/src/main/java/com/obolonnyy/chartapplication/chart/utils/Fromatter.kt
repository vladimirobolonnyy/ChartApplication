package com.obolonnyy.chartapplication.chart.utils

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale.Builder().setLanguage("ru").setScript("Cyrl").build()
private val hoursFormatter = DateTimeFormat.forPattern("HH:mm")
private val daysFormatter = SimpleDateFormat("LLLL ‘yy", locale)

private val symbols = DecimalFormatSymbols().apply {
    groupingSeparator = ' '
    decimalSeparator = '.'
}

private val twoFormatter = DecimalFormat("###,##0.00", symbols).apply {
    roundingMode = RoundingMode.DOWN
    maximumFractionDigits = 4
    decimalFormatSymbols = symbols
    groupingSize = 3
}

private val fourFormatter = DecimalFormat("###,##0.0000", symbols).apply {
    roundingMode = RoundingMode.DOWN
    maximumFractionDigits = 4
    decimalFormatSymbols = symbols
    groupingSize = 3
}

private val intFormatter = DecimalFormat("###,###", symbols).apply {
    roundingMode = RoundingMode.DOWN
    decimalFormatSymbols = symbols
    groupingSize = 3
}

internal fun formatMoney(money: Double, showCentsNumber: Int, currency: String = "₽"): String {
    val formatter: DecimalFormat = when (showCentsNumber) {
        0 -> intFormatter
        2 -> twoFormatter
        4 -> fourFormatter
        else -> throw IllegalStateException("Не знаю, как форматировать ,$showCentsNumber знаков после запятой")
    }
    val format = formatter.format(money)
    return "$format $currency".trim()
}

internal fun formatHoursDate(date: LocalDateTime): String {
    return hoursFormatter.print(date)
}

internal fun formatDaysDate(date: LocalDateTime): String {
    return daysFormatter.format(date.toDate()).capitalize()
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}