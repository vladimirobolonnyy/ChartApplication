package com.obolonnyy.chartapplication.chart.data

import org.joda.time.LocalDateTime

interface PointX {
    val x: Float
}


/**
 * х,у - координаты точки  по которым рисуется график
 * time - время точки (переводится в х)
 * value - значение точки (переводится в y)
 */
data class Point(
    override val x: Float,
    val y: Float,
    val dateTime: LocalDateTime,
    val value: Double = 0.0
) : PointX
