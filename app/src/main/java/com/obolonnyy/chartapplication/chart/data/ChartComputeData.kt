package com.obolonnyy.chartapplication.chart.data

import androidx.compose.ui.geometry.Size
import com.obolonnyy.chartapplication.chart.utils.PointX
import com.obolonnyy.chartapplication.chart.utils.search

// координаты точки х,у, по которым рисуется график
data class Point(override val x: Float, val y: Float, val value: Double) : PointX

data class AdditionalPoint(
    override val x: Float,
    val y: Float,
    val descriptionFirstValue: String? = null,
    val descriptionSecondValue: String? = null
) : PointX {
    val lines = when {
        descriptionFirstValue != null && descriptionSecondValue != null -> 2
        descriptionFirstValue != null || descriptionSecondValue != null -> 1
        else -> 0
    }
}

data class ChartComputeData(
    val points: ArrayList<Point> = ArrayList(),
    val additionalPoints: ArrayList<AdditionalPoint> = ArrayList()
) {
    // количество дат под графиком
    private val datesNumber = 4
    val texts = getDates(datesNumber)

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

    fun findAdditionalPointByX(x: Float): AdditionalPoint? {
        return when (additionalPoints.size) {
            0 -> null
            1 -> additionalPoints.first()
            else -> {
                val point1 = additionalPoints.search(x)
                val point2 = points.search(x)
                point1.takeIf { point1.x == point2.x }
            }
        }
    }

    private fun getDates(datesNumber: Int): List<String> {
        val size = points.size - 1
        val step = size / (datesNumber - 1).toFloat()
        val resultList = mutableListOf<String>()
        for (i in 0 until this.datesNumber) {
            val index = (i * step).toInt()
            val j = points[index]

            //todo format date
            resultList.add("text${j.y}")
        }
        return resultList
    }
}

fun ChartComputeData.getValueByY(searchY: Float): Double {
    val diffPixels = maxY - minY
    val diffValues = maxValueY - minValueY

    // на ноль не делим
    if (diffPixels == 0f) return points.first().value
    return (((maxY - searchY) / diffPixels) * diffValues) + minValueY
}


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

fun ChartInputData.toChartComputeData(size: Size): ChartComputeData {

    val inputData = this
    val y = size.height / 2
    val point = Point(x = size.width / 2, y = y, value = inputData.value)
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
    return ChartComputeData(ArrayList(listOf(point)), ArrayList(listOfNotNull(additionalPoint)))
}