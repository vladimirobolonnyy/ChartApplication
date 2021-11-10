package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.obolonnyy.chartapplication.chart.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.gradiendMainBottom
import com.obolonnyy.chartapplication.chart.utils.gradiendMainTop

/**
 * рисует градиент под графиком
 */
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

private fun <T> List<T>.second(): T {
    if (size < 1) throw NoSuchElementException("List has less than 2 elements")
    return this[1]
}