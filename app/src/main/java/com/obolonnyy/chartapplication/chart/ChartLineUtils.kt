package com.obolonnyy.chartapplication.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.drawChartLine(data: ChartComputeData) {
    for (i in 0 until data.pointsSize - 1) {
        val point1 = data.points[i]
        val point2 = data.points[i + 1]
        drawLine(
            start = Offset(
                x = point1.x,
                y = point1.y
            ),
            end = Offset(
                x = point2.x,
                y = point2.y
            ),
            color = Color(40, 193, 218),
            strokeWidth = Stroke.DefaultMiter
        )
    }
}