package com.obolonnyy.chartapplication.chart.data

import androidx.compose.ui.geometry.Size
import com.obolonnyy.chartapplication.chart.utils.search

// координаты экрана х,у, по которым рисуется график
data class Point(val x: Float, val y: Float, val realValue: Double)

data class ChartComputeData(val points: ArrayList<Point>) {

    val texts = listOf("Text1", "Text2", "Text3", "Text4")

    val pointsSize = points.size
    val maxY: Float = points.maxByOrNull { it.y }?.y ?: 0f
    val minY: Float = points.minByOrNull { it.y }?.y ?: 0f

    val maxValueY: Double = points.maxByOrNull { it.realValue }?.realValue ?: 0.0
    val minValueY: Double = points.minByOrNull { it.realValue }?.realValue ?: 0.0

    fun findPointByX(x: Float): Point? {
        return when (pointsSize) {
            0 -> null
            1 -> points.first()
            else -> points.search(x)
        }
    }
}

fun ChartComputeData.getValueByY(searchY: Float): Double {
    val diffPixels = maxY - minY
    val diffValues = maxValueY - minValueY
    return (((maxY - searchY) / diffPixels) * diffValues) + minValueY
}

fun TransactionsPerSecond.toDate(
    size: Size,
    chartPaddingTop: Float,
    chartPaddingBottom: Float
): ChartComputeData {

    // Total number of transactions.
    val totalRecords = this.transactions.size

    // Maximum distance between dots (transactions)
    val lineDistance = size.width / (totalRecords - 1)

    // Canvas height
    val cHeight = size.height - chartPaddingTop - chartPaddingBottom

    val diffValues = maxTransaction - minTransaction

    val points = this.transactions.mapIndexed { index, transactionRate ->
        val x = lineDistance * (index)
        val y =
            (maxTransaction - transactionRate.transactionsPerSecondValue) / diffValues * cHeight + chartPaddingTop
        Point(x, y.toFloat(), transactionRate.transactionsPerSecondValue)
    }

    return ChartComputeData(ArrayList(points))
}