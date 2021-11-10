package com.obolonnyy.chartapplication.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

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


internal fun DrawScope.drawChartGradientBackground(data: ChartComputeData) {
    val gradientPath = Path().let {
        val pointFirst = data.points.first()
        val pointSecond = data.points.second()
        val pointLast = data.points.last()

        val halfx = (pointSecond.x - pointFirst.x) / 2
        it.moveTo(pointFirst.x, pointFirst.y)

        data.points.forEach { point ->
            //todo почистить relativeQuadraticBezierTo, если не получится его сделать
//            it.relativeQuadraticBezierTo(point.x, point.y, halfx, point.y)
            it.lineTo(point.x, point.y)
        }
        it.lineTo(pointLast.x, this.size.height)
        it.lineTo(pointFirst.x, this.size.height)
        it.lineTo(pointFirst.x, pointFirst.y)
        it.close()
        it
    }

    drawPath(
        path = gradientPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                gradiendMainTop,
                gradiendMainBottom
            )
        ),
    )
}