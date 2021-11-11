package com.obolonnyy.chartapplication.chart.data

import androidx.compose.ui.geometry.Size
import com.obolonnyy.chartapplication.chart.utils.search

// координаты точки х,у, по которым рисуется график
data class Point(val x: Float, val y: Float, val value: Double)

data class AdditionalPoint(
    val x: Float,
    val y: Float,
    val descriptionFirstValue: String? = null,
    val descriptionSecondValue: String? = null
)

data class ChartComputeData(
    val points: ArrayList<Point>,
    val additionalPoints: ArrayList<AdditionalPoint> = ArrayList()
) {

    val texts = listOf("Text1", "Text2", "Text3", "Text4")

    val pointsSize = points.size
    val maxY: Float = points.maxByOrNull { it.y }?.y ?: 0f
    val minY: Float = points.minByOrNull { it.y }?.y ?: 0f

    val maxValueY: Double = points.maxByOrNull { it.value }?.value ?: 0.0
    val minValueY: Double = points.minByOrNull { it.value }?.value ?: 0.0

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

    return ChartComputeData(ArrayList(points), ArrayList())
}

fun List<ChartInputData>.toChartComputeData(
    size: Size,
    chartPaddingTop: Float,
    chartPaddingBottom: Float
): ChartComputeData {

    val totalRecords = this.size
    val lineDistance = size.width / (totalRecords - 1)
    val cHeight = size.height - chartPaddingTop - chartPaddingBottom

    val maxValue = this.maxOf { it.value }
    val minValue = this.minOf { it.value }
    val diffValues = maxValue - minValue

    val resultPoints = mutableListOf<Point>()
    val additionalPoints = mutableListOf<AdditionalPoint>()
    this.forEachIndexed { index, inputData ->
        val x = lineDistance * (index)
        val y = ((maxValue - inputData.value) / diffValues * cHeight + chartPaddingTop).toFloat()

        resultPoints.add(Point(x = x, y = y, value = inputData.value))

        if (inputData.hasValue) {
            additionalPoints.add(
                AdditionalPoint(
                    x = x,
                    y = y,
                    descriptionFirstValue = inputData.descriptionFirstValue,
                    descriptionSecondValue = inputData.descriptionSecondValue,
                )
            )
        }
    }

    return ChartComputeData(ArrayList(resultPoints), ArrayList(additionalPoints))
}