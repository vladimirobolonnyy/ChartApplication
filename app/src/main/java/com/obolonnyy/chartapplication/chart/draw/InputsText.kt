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
import com.obolonnyy.chartapplication.chart.data.AdditionalPoint
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.black
import com.obolonnyy.chartapplication.chart.utils.green
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

    // todo
    // получилось много кода
    // мб как-нибудь надо зарефакторить
    // или как-то просто отрисовать вьюху


    val point = data.findAdditionalPointByX(state.x) ?: return
    if (point.lines() == 0) return

    val paddingHorizontal = 12.dp.toPx()
    val paddingVertical = 5.dp.toPx()
    val marginTop = 10.dp.toPx()

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 10.dp.toPx()
        this.color = white.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val greenPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 10.dp.toPx()
        this.color = green.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val text1_1 = "some1 "
    val text1_2 = point.descriptionFirstValue.orEmpty()
    val text1 = text1_1 + text1_2
    val text2 = "some2 " + point.descriptionSecondValue

    val text1_1Size = textPaint.measureText(text1_1)
    val text1Size = textPaint.measureText(text1)
    val text2Size = textPaint.measureText(text2)

    val maxTextWidth = max(text1Size, text2Size) + paddingHorizontal * 2
    val textHeight = textPaint.getTextHeight()
    val maxTextHeight = paddingVertical * 2 + 3.dp.toPx() + textHeight * point.lines()

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
            height = maxTextHeight,
        ),
        cornerRadius = CornerRadius(10f)
    )

    var text2y = marginTop + paddingVertical + textHeight * 2 + 1.dp.toPx()

    if (point.descriptionFirstValue != null) {
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text1_1,
                x + paddingHorizontal,
                marginTop + paddingVertical + textHeight - 1.dp.toPx(),
                textPaint
            )
        }
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text1_2,
                x + paddingHorizontal + text1_1Size,
                marginTop + paddingVertical + textHeight - 1.dp.toPx(),
                greenPaint
            )
        }
    } else {
        text2y = marginTop + paddingVertical + textHeight - 1.dp.toPx()
    }

    if (point.descriptionSecondValue != null) {
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text2,
                x + paddingHorizontal,
                text2y,
                textPaint
            )
        }
    }
}

private fun AdditionalPoint.lines() = when {
    descriptionFirstValue != null && descriptionSecondValue != null -> 2
    descriptionFirstValue != null || descriptionSecondValue != null -> 1
    else -> 0
}

private fun NativePaint.getTextHeight(): Float {
    val fm = this.fontMetrics
    return fm.descent - fm.ascent
}