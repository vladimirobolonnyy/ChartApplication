package com.obolonnyy.chartapplication.chart

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.obolonnyy.chartapplication.chart.data.ChartInputData
import com.obolonnyy.chartapplication.chart.data.toChartComputeData
import com.obolonnyy.chartapplication.chart.draw.*
import drawAxisXDark
import drawAxisXLight


private val chartState: MutableState<ChartPressedState> =
    mutableStateOf(ChartPressedState.Unpressed)

@ExperimentalComposeUiApi
@Composable
fun ComposeChart(
    modifier: Modifier = Modifier,
    transactionsPerSecond: List<ChartInputData>
) {
    if (transactionsPerSecond.isEmpty()) return
    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInteropFilter { filterPointer(it) }
    ) {
        renderChart(transactionsPerSecond)
    }
}

private fun filterPointer(event: MotionEvent): Boolean {
    when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            chartState.value = ChartPressedState.PressOneFinger(event.x, event.y)
        }
        MotionEvent.ACTION_POINTER_DOWN -> {
            if (event.pointerCount <= 1) {
                chartState.value = ChartPressedState.Unpressed
            } else {
                chartState.value = ChartPressedState.PressTwoFingers(
                    event.getX(0),
                    event.getY(0),
                    event.getX(1),
                    event.getY(1),
                )
            }
        }
        MotionEvent.ACTION_MOVE -> {
            if (event.pointerCount <= 1) {
                chartState.value = ChartPressedState.PressOneFinger(event.x, event.y)
            } else {
                chartState.value = ChartPressedState.PressTwoFingers(
                    event.getX(0),
                    event.getY(0),
                    event.getX(1),
                    event.getY(1),
                )
            }
        }
        else -> {
            chartState.value = ChartPressedState.Unpressed
            return false
        }
    }
    return true
}

private fun DrawScope.renderChart(transactionsPerSecond: List<ChartInputData>) {
    val chartPaddingTop: Float = 60.dp.toPx()
    val chartPaddingBottom: Float = 35.dp.toPx()
    val data = transactionsPerSecond.toChartComputeData(size, chartPaddingTop, chartPaddingBottom)

    // тут рисуем разные стейты в не зависимости от того, нажат график или нет
    when (val chartState = chartState.value) {
        is ChartPressedState.PressOneFinger -> {
            this.drawChartGradientBackground(data.points)
            this.drawVerticalLine(chartState, data, chartPaddingBottom)
            this.drawPoint(chartState, data)
            this.drawAxisXDark(data, size, chartPaddingTop, chartPaddingBottom)
            this.drawBottomDatesDark(data, size)
            this.drawInputText(chartState, data)
        }
        is ChartPressedState.PressTwoFingers -> {
            this.drawChartGradientBackgroundBetween(chartState, data)
            this.drawVerticalLine(chartState, data, chartPaddingBottom)
            this.drawPoint(chartState, data)
            this.drawAxisXDark(data, size, chartPaddingTop, chartPaddingBottom)
            this.drawBottomDatesDark(data, size)
        }
        is ChartPressedState.Unpressed -> {
            this.drawChartGradientBackground(data.points)
            this.drawAxisXLight(data, size, chartPaddingTop, chartPaddingBottom)
            this.drawBottomDatesLight(data, size)
        }
    }

    // тут рисуем всё, что должно отображаться в не зависимости от того, нажат график или нет
    this.drawChartLine(data, size)
    this.drawChartLineDots(data)
}


// lineDrawer
// xAxisDrawer
// yAxisDrawer
// xlabelDrawer
// ylabelDrawer