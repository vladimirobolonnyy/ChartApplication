package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
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
internal fun DrawScope.drawChartGradientBackground(
    points: ArrayList<Point>,
    animatedFloatState: State<Float>
) = clipPath(
    // clipPath - обрезает вьюху и тем самым мы достигаем плавной анимации
    Path().apply { addRect(Rect(0f, 0f, size.width * animatedFloatState.value, size.height)) }
) {
    val path = Path()
    val firstPoint = points.first()
    val lastPoint = points.last()

    val maxY = size.height

    path.moveTo(firstPoint.x, maxY)
    path.lineTo(firstPoint.x, firstPoint.y)

    val gradientPath = points.getCubicPath(path)

    gradientPath.lineTo(lastPoint.x, maxY)
    gradientPath.lineTo(firstPoint.x, maxY)

    drawPath(
        path = gradientPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                gradiendMainTop,
                gradiendMainBottom
            ),
        ),
    )
}

/**
 * рисует градиент между двумя точками
 */
internal fun DrawScope.drawChartGradientBackgroundBetween(
    state: ChartPressedState.PressTwoFingers,
    data: ChartComputeData
) {
    val point1 = data.findPointByX(state.x1) ?: return
    val point2 = data.findPointByX(state.x2) ?: return
    val i1 = data.points.indexOf(point1)
    val i2 = data.points.indexOf(point2)
    val resultList = data.points.subList(min(i1, i2), max(i1, i2) + 1)
    this.drawChartGradientBackground(ArrayList(resultList), mutableStateOf(1f))
}