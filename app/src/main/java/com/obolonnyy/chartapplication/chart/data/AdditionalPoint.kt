package com.obolonnyy.chartapplication.chart.data

data class AdditionalPoint(
    override val x: Float,
    val y: Float,
    val descriptionFirstValue: String? = null,
    val descriptionSecondValue: String? = null
) : PointX