package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.inputDotColor
import com.obolonnyy.chartapplication.chart.utils.white


/**
 * рисует дополнительные точки вводы-вывода
 */
internal fun DrawScope.drawChartLineDots(data: ChartComputeData) {
    data.additionalPoints.forEach { point ->
        drawCircle(
            color = inputDotColor,
            radius = 8.toDp().toPx(),
            center = Offset(point.x, point.y)
        )
        drawCircle(
            color = white,
            radius = 6.toDp().toPx(),
            center = Offset(point.x, point.y)
        )
    }
}