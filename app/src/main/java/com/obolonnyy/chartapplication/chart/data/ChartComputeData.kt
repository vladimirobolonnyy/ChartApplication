package com.obolonnyy.chartapplication.chart.data

import com.obolonnyy.chartapplication.chart.utils.search


data class ChartComputeData(
    val points: ArrayList<Point> = ArrayList(),
    val additionalPoints: ArrayList<AdditionalPoint> = ArrayList(),
    val texts: List<String> = ArrayList(),
) {

    private val maxY: Float = points.maxByOrNull { it.y }?.y ?: 0f
    private val minY: Float = points.minByOrNull { it.y }?.y ?: 0f

    private val maxValueY: Double = points.maxByOrNull { it.value }?.value ?: 0.0
    private val minValueY: Double = points.minByOrNull { it.value }?.value ?: 0.0

    fun findPointByX(x: Float): Point? {
        return when (points.size) {
            0 -> null
            1 -> points.first()
            else -> points.search(x)
        }
    }

    fun getValueByY(searchY: Float): Double {
        val diffPixels = maxY - minY
        val diffValues = maxValueY - minValueY

        // на ноль не делим
        if (diffPixels == 0f) return points.first().value
        return (((maxY - searchY) / diffPixels) * diffValues) + minValueY
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
}