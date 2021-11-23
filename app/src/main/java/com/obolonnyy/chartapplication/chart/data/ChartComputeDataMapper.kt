package com.obolonnyy.chartapplication.chart.data

import androidx.compose.ui.geometry.Size
import com.obolonnyy.chartapplication.chart.utils.formatDaysDate

private const val datesNumber = 4

/**
 * Считаем данные для списка точек
 */
fun List<ChartInputData>.toChartComputeData(
    size: Size,
    chartPaddingTop: Float,
    chartPaddingBottom: Float
): ChartComputeData {

    if (this.isEmpty()) {
        return ChartComputeData()
    }
    if (this.size == 1) {
        return this.first().toChartComputeData(size)
    }

    val cHeight = size.height - chartPaddingTop - chartPaddingBottom
    val maxValue = this.maxOf { it.value }
    val minValue = this.minOf { it.value }
    val diffValues = maxValue - minValue

    val totalRecords = this.size
    val lineDistance = size.width / (totalRecords - 1)

    val resultPoints = mutableListOf<Point>()
    val additionalPoints = mutableListOf<AdditionalPoint>()
    this.forEachIndexed { index, inputData ->
        val x = lineDistance * (index)
        val y = ((maxValue - inputData.value) / diffValues * cHeight + chartPaddingTop).toFloat()
        resultPoints.add(
            Point(
                x = x,
                y = y,
                value = inputData.value,
                dateTime = inputData.dateTime
            )
        )

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

    return ChartComputeData(
        points = ArrayList(resultPoints),
        additionalPoints = ArrayList(additionalPoints),
        texts = getDates(resultPoints, datesNumber)
    )
}

/**
 * Считаем данные для 1й точки
 */
fun ChartInputData.toChartComputeData(size: Size): ChartComputeData {
    val inputData = this
    val y = size.height / 2
    val point = Point(
        x = size.width / 2,
        y = y,
        value = inputData.value,
        dateTime = inputData.dateTime
    )
    val additionalPoint = if (inputData.hasValue) {
        AdditionalPoint(
            x = size.width / 2,
            y = y,
            descriptionFirstValue = inputData.descriptionFirstValue,
            descriptionSecondValue = inputData.descriptionSecondValue,
        )
    } else {
        null
    }
    return ChartComputeData(
        points = ArrayList(listOf(point)),
        additionalPoints = ArrayList(listOfNotNull(additionalPoint)),
        texts = getDates(point)
    )
}


private fun getDates(points: List<Point>, datesNumber: Int): List<String> {
    val size = points.size - 1
    val step = size / (datesNumber - 1).toFloat()
    val resultList = mutableListOf<String>()
    for (i in 0 until datesNumber) {
        val index = (i * step).toInt()
        val text = formatDaysDate(points[index].dateTime)
        resultList.add(text)
    }
    return resultList
}

private fun getDates(point: Point): List<String> {
    return listOf(formatDaysDate(point.dateTime))
}