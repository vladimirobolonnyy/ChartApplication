package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.obolonnyy.chartapplication.chart.ChartPressedState
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.data.Point
import com.obolonnyy.chartapplication.chart.utils.gradiendMainBottom
import com.obolonnyy.chartapplication.chart.utils.gradiendMainTop
import kotlin.math.max
import kotlin.math.min

/**
 * рисует градиент под графиком
 */
internal fun DrawScope.drawChartGradientBackground(points: List<Point>) {
    val gradientPath = Path().let {
        val pointFirst = points.firstOrNull() ?: return
        val pointLast = points.last()

        it.moveTo(pointFirst.x, pointFirst.y)

        points.forEach { point ->
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

internal fun DrawScope.drawChartGradientBackgroundBetween(
    state: ChartPressedState.PressTwoFingers,
    data: ChartComputeData
) {
    val point1 = data.findPointByX(state.x1) ?: return
    val point2 = data.findPointByX(state.x2) ?: return
    val i1 = data.points.indexOf(point1)
    val i2 = data.points.indexOf(point2)
    val resultList = data.points.subList(min(i1, i2), max(i1, i2) + 1)
    this.drawChartGradientBackground(resultList)

}

private fun <T> List<T>.second(): T {
    if (size < 1) throw NoSuchElementException("List has less than 2 elements")
    return this[1]
}