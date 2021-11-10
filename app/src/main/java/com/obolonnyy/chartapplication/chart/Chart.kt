package com.obolonnyy.chartapplication.chart

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInteropFilter

/**
 * Represents a group of Transactions
 * @param maxTransaction the max transaction value in the list of transactions previously calculated in some repository.
 * @param transactions list of transactions per second.
 */
data class TransactionsPerSecond(
    val transactions: List<TransactionRate>,
) {
    val maxTransaction: Double =
        transactions.maxByOrNull { it.transactionsPerSecondValue }?.transactionsPerSecondValue
            ?: 0.0
}


/**
 * Represents a transaction rate.
 * @param timeStamp the time stamp of the transaction.
 * @param transactionsPerSecondValue the quantity of transactions made per second.
 */
data class TransactionRate(
    val timeStamp: Long,
    val transactionsPerSecondValue: Double
)

/**
 * Calculates the Y pixel coordinate for a given transaction rate.
 *
 * @param higherTransactionRateValue the highest rate value in the whole list of transactions.
 * @param currentTransactionRate the current transaction RATE while iterating the list of transactions.
 * @param canvasHeight the canvas HEIGHT for draw the linear chart.
 *
 * @return [Float] Y coordinate for a transaction rate.
 */
fun calculateYCoordinate(
    higherTransactionRateValue: Double,
    currentTransactionRate: Double,
    canvasHeight: Float
): Float {
    val maxAndCurrentValueDifference = (higherTransactionRateValue - currentTransactionRate)
        .toFloat()
    val relativePercentageOfScreen = (canvasHeight / higherTransactionRateValue)
        .toFloat()
    return maxAndCurrentValueDifference * relativePercentageOfScreen
}


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
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    chartState.value = ChartPressedState.PressOneFinger(it.x, it.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    chartState.value = ChartPressedState.PressOneFinger(it.x, it.y)
                }
                MotionEvent.ACTION_UP -> {
                    chartState.value = ChartPressedState.Unpressed
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
                this.drawChartGradientBackground(data)
                this.drawVerticalLine(chartState, data)
                this.drawPoint(chartState, data)
                this.drawAxisXDark(data, size)
                this.drawBottomDatesDark(data, size)
            }
            is ChartPressedState.PressTwoFingers -> {

            }
            is ChartPressedState.Unpressed -> {
                this.drawChartGradientBackground(data)
                this.drawAxisXLight(data, size)
                this.drawBottomDatesLight(data, size)
            }
        }

        this.drawChartLine(data)
    }
}

fun DrawScope.drawPoint(state: ChartPressedState.PressOneFinger, data: ChartComputeData) {
    val point = data.findPointByX(state.x) ?: return
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


// lineDrawer
// xAxisDrawer
// yAxisDrawer
// xlabelDrawer
// ylabelDrawer