package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.data.Point
import com.obolonnyy.chartapplication.chart.utils.chartLineColor


/**
 * рисует линию графика
 */
internal fun DrawScope.drawChartLine(
    data: ChartComputeData,
    animatedFloatState: State<Float>
) = clipPath(
    // clipPath - обрезает вьюху и тем самым мы достигаем плавной анимации
    Path().apply { addRect(Rect(0f, 0f, size.width * animatedFloatState.value, size.height)) }
) {
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
                    x = size.width * animatedFloatState.value,
                    y = point1.y
                ),
                color = chartLineColor,
                strokeWidth = 1.toDp().toPx()
            )
        }
        else -> {
            val cubicPath = data.points.getCubicPath(Path())

            drawPath(
                path = cubicPath,
                color = chartLineColor,
                style = Stroke(width = 1.toDp().toPx()),
            )

        }
    }
}


/**
 * Рисует сглаженную прямую
 * */
internal fun ArrayList<Point>.getCubicPath(path: Path): Path {
    var prev = this.first()
    var current = prev
    path.moveTo(current.x, current.y)
    for (j in 1 until this.size) {
        prev = current
        current = this[j]
        val cpx = prev.x + (current.x - prev.x) / 2.0f
        path.cubicTo(
            x1 = cpx,
            y1 = prev.y,
            x2 = cpx,
            y2 = current.y,
            x3 = current.x,
            y3 = current.y
        )
    }
    return path
}