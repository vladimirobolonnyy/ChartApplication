package com.obolonnyy.chartapplication.chart.draw

import android.graphics.Typeface
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.obolonnyy.chartapplication.chart.ChartPressedState
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.black
import com.obolonnyy.chartapplication.chart.utils.white
import kotlin.math.max


/**
 * Рисует плашку Введено: ..., Выведено: ... при нажатии на точку графика
 * где есть вводы-выводы
 */
internal fun DrawScope.drawInputText(
    state: ChartPressedState.PressOneFinger,
    data: ChartComputeData
) {

    val point = data.findAdditionalPointByX(state.x) ?: return

    val paddingHorizontal = 12.dp.toPx()
    val paddingVertical = 5.dp.toPx()
    val marginTop = 10.dp.toPx()

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 10.dp.toPx()
        this.color = white.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val text1 = "some1 " + point.descriptionFirstValue
    val text2 = "some2 " + point.descriptionSecondValue

    val text1Size = textPaint.measureText(text1)
    val text2Size = textPaint.measureText(text2)

    val maxTextWidth = max(text1Size, text2Size) + paddingHorizontal * 2
    val textHeight = textPaint.getTextHeight()

    val x = when {
        point.x - (maxTextWidth / 2) < 0 -> 0f
        point.x + (maxTextWidth / 2) > size.width -> size.width - maxTextWidth
        else -> point.x - (maxTextWidth / 2)
    }

    drawRoundRect(
        color = black,
        topLeft = Offset(x, marginTop),
        size = Size(
            width = maxTextWidth,
            height = textHeight * 2 + paddingVertical * 2 + 3.dp.toPx(),
        ),
        cornerRadius = CornerRadius(10f)
    )

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            text1,
            x + paddingHorizontal,
            marginTop + paddingVertical + textHeight - 1.dp.toPx(),
            textPaint
        )
    }

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            text2,
            x + paddingHorizontal,
            marginTop + paddingVertical + textHeight * 2 + 1.dp.toPx(),
            textPaint
        )
    }
}

private fun NativePaint.getTextHeight(): Float {
    val fm = this.fontMetrics
    return fm.descent - fm.ascent
}