package com.obolonnyy.chartapplication.chart

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.obolonnyy.chartapplication.chart.data.TransactionsPerSecond
import com.obolonnyy.chartapplication.chart.data.toDate
import com.obolonnyy.chartapplication.chart.draw.*
import drawAxisXDark
import drawAxisXLight


private val chartState: MutableState<ChartPressedState> =
    mutableStateOf(ChartPressedState.Unpressed)

@ExperimentalComposeUiApi
@Composable
fun ComposeChart(
    modifier: Modifier = Modifier,
    transactionsPerSecond: TransactionsPerSecond
) {

    if (transactionsPerSecond.transactions.isEmpty()) return

    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInteropFilter {
            println("MotionEvent , ${it}")
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    chartState.value = ChartPressedState.PressOneFinger(it.x, it.y)
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    if (it.pointerCount <= 1) {
                        chartState.value = ChartPressedState.Unpressed
                    } else {
                        chartState.value = ChartPressedState.PressTwoFingers(
                            it.getX(0),
                            it.getY(0),
                            it.getX(1),
                            it.getY(1),
                        )
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (it.pointerCount <= 1) {
                        chartState.value = ChartPressedState.PressOneFinger(it.x, it.y)
                    } else {
                        chartState.value = ChartPressedState.PressTwoFingers(
                            it.getX(0),
                            it.getY(0),
                            it.getX(1),
                            it.getY(1),
                        )
                    }
                }
                else -> {
                    chartState.value = ChartPressedState.Unpressed
                    false
                }
            }
            true
        }) {

        val data = transactionsPerSecond.toDate(size)

        when (val chartState = chartState.value) {
            is ChartPressedState.PressOneFinger -> {
                this.drawChartGradientBackground(data.points)
                this.drawVerticalLine(chartState, data)
                this.drawPoint(chartState, data)
                this.drawAxisXDark(data, size)
                this.drawBottomDatesDark(data, size)
            }
            is ChartPressedState.PressTwoFingers -> {
                this.drawChartGradientBackgroundBetween(chartState, data)
                this.drawVerticalLine(chartState, data)
                this.drawVerticalLine(chartState, data)
                this.drawPoint(chartState, data)
                this.drawPoint(chartState, data)
                this.drawAxisXDark(data, size)
                this.drawBottomDatesDark(data, size)

            }
            is ChartPressedState.Unpressed -> {
                this.drawChartGradientBackground(data.points)
                this.drawAxisXLight(data, size)
                this.drawBottomDatesLight(data, size)
            }
        }

        this.drawChartLine(data)
    }
}


// lineDrawer
// xAxisDrawer
// yAxisDrawer
// xlabelDrawer
// ylabelDrawer