package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.data.Point
import com.obolonnyy.chartapplication.chart.utils.chartLineColor


/**
 * рисует линию графика
 */
internal fun DrawScope.drawChartLine(data: ChartComputeData, size: Size) {
    when {
        data.points.isEmpty() -> return
        data.points.size == 1 -> {
            val point1 = data.points.first()
            drawLine(
                start = Offset(
                    x = 0f,
                    y = point1.y
                ),
                end = Offset(
                    x = size.width,
                    y = point1.y
                ),
                color = chartLineColor,
                strokeWidth = 1.toDp().toPx()
            )
        }
        else -> {
            val cubicPath = data.points.drawCubicPath(Path())
            drawPath(
                path = cubicPath,
                color = chartLineColor,
                style = Stroke(width = 1.toDp().toPx())
            )
        }
    }
}


/**
 * Рисует сглаженную прямую
 * */
internal fun ArrayList<Point>.drawCubicPath(path: Path): Path {
    val pointFirst = this.first()

    var prev = pointFirst
    var current = prev
    val phaseY = 1f

    path.moveTo(current.x, current.y * phaseY)

    for (j in 1 until this.size) {
        prev = current
        current = this[j]
        val cpx = prev.x + (current.x - prev.x) / 2.0f
        path.cubicTo(
            x1 = cpx,
            y1 = prev.y * phaseY,
            x2 = cpx,
            y2 = current.y * phaseY,
            x3 = current.x,
            y3 = current.y * phaseY
        )
    }
    return path
}