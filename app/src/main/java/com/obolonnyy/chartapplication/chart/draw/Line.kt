package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.chartLineColor


/**
 * рисует линию графика
 */
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
            color = chartLineColor,
            strokeWidth = 1.toDp().toPx()
        )
    }
}