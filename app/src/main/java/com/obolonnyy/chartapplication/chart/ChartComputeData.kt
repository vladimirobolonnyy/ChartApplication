package com.obolonnyy.chartapplication.chart

import androidx.compose.ui.geometry.Size
import com.obolonnyy.chartapplication.chart.utils.search

// координаты экрана х,у, по которым рисуется график
data class Point(val x: Float, val y: Float)

data class ChartComputeData(
    val points: ArrayList<Point>
) {

    val texts = listOf("Text1", "Text2", "Text3", "Text4")

    val pointsSize = points.size
    val maxY = points.maxByOrNull { it.y } ?: 0.0
    val minY = points.minByOrNull { it.y } ?: 0.0
    val maxX = points.maxByOrNull { it.x } ?: 0.0
    val minX = points.minByOrNull { it.x } ?: 0.0

    fun findPointByX(x: Float): Point? {
        return when (pointsSize) {
            0 -> null
            1 -> points.first()
            else -> points.search(x)
        }
    }

    fun findPointByY(x: Float): Point? {
        return when (pointsSize) {
            0 -> null
            1 -> points.first()
            else -> points.search(x)
        }
    }
}

fun TransactionsPerSecond.toDate(size: Size): ChartComputeData {
    // Total number of transactions.
    val totalRecords = this.transactions.size

    // Maximum distance between dots (transactions)
    val lineDistance = size.width / (totalRecords - 1)

    // Canvas height
    val cHeight = size.height

    var currentLineDistance = lineDistance

    val points = this.transactions.mapIndexed { index, transactionRate ->
        val x = lineDistance * (index)
        val y = calculateYCoordinate(
            higherTransactionRateValue = this.maxTransaction,
            currentTransactionRate = transactionRate.transactionsPerSecondValue,
            canvasHeight = cHeight
        )
        Point(x, y)
    }

    return ChartComputeData(ArrayList(points))
}