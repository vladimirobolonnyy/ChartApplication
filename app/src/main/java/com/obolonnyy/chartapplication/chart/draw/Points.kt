package com.obolonnyy.chartapplication.chart.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.obolonnyy.chartapplication.chart.ChartPressedState
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.data.Point
import com.obolonnyy.chartapplication.chart.utils.black
import com.obolonnyy.chartapplication.chart.utils.white

/**
 * Когда пользователь кликает на график - рисует точку в месте пересечения вертикальной линии
 * и графика
 */

fun DrawScope.drawPoint(state: ChartPressedState.PressOneFinger, data: ChartComputeData) {
    val point = data.findPointByX(state.x) ?: return
    drawPoint(point)
}

fun DrawScope.drawPoint(state: ChartPressedState.PressTwoFingers, data: ChartComputeData) {
    val point1 = data.findPointByX(state.x1) ?: return
    val point2 = data.findPointByX(state.x2) ?: return
    this.drawPoint(point1)
    this.drawPoint(point2)
}

private fun DrawScope.drawPoint(point: Point) {
    drawCircle(
        color = white,
        radius = 11.toDp().toPx(),
        center = Offset(point.x, point.y)
    )
    drawCircle(
        color = black,
        radius = 7.toDp().toPx(),
        center = Offset(point.x, point.y)
    )
}


fun DrawScope.drawVerticalLine(state: ChartPressedState.PressOneFinger, data: ChartComputeData) {
    val point = data.findPointByX(state.x) ?: return
    this.drawVerticalLine(point)
}

fun DrawScope.drawVerticalLine(state: ChartPressedState.PressTwoFingers, data: ChartComputeData) {
    val point1 = data.findPointByX(state.x1) ?: return
    val point2 = data.findPointByX(state.x2) ?: return
    this.drawVerticalLine(point1)
    this.drawVerticalLine(point2)
}

fun DrawScope.drawVerticalLine(point: Point) {
    drawLine(
        start = Offset(
            x = point.x,
            y = 0f
        ),
        end = Offset(
            x = point.x,
            y = size.height
        ),
        color = black,
        strokeWidth = 2.toDp().toPx()
    )
}