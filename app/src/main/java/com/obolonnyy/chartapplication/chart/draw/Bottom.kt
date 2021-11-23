package com.obolonnyy.chartapplication.chart.draw

import android.graphics.Typeface
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.obolonnyy.chartapplication.chart.data.ChartComputeData
import com.obolonnyy.chartapplication.chart.utils.horizontalBrushColorDark
import com.obolonnyy.chartapplication.chart.utils.horizontalBrushColorLight

/**
 * рисует подписи под графиком
 */
internal fun DrawScope.drawBottomDatesDark(data: ChartComputeData, size: Size) {
    this.drawBottomDates(data, size, horizontalBrushColorDark)
}

internal fun DrawScope.drawBottomDatesLight(data: ChartComputeData, size: Size) {
    this.drawBottomDates(data, size, horizontalBrushColorLight)
}

private fun DrawScope.drawBottomDates(data: ChartComputeData, size: Size, color: Color) {
    val texts = data.texts
    val firstText = texts.first()
    val marginStart = 16.dp.toPx()
    val marginEnd = 16.dp.toPx()
    val marginBottom = 10.dp.toPx()
    val textsNumber = data.texts.size

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 10.dp.toPx()
        this.color = color.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
    }

    val maxWidth = size.width - marginStart - marginEnd
    // допускаем, что все текста ± одинаковы по длине
    val textWidth = textPaint.measureText(firstText)
    val space = (maxWidth - textWidth * textsNumber) / (textsNumber - 1)
    val textY = size.height - marginBottom

    // первый текст рисуем у левого края
    drawIntoCanvas {
        it.nativeCanvas.drawText(texts.first(), marginStart, textY, textPaint)
    }
    // послений текст рисуем у правого края
    drawIntoCanvas {
        it.nativeCanvas.drawText(texts.last(), size.width - marginEnd - textWidth, textY, textPaint)
    }

    // остальные текста по серединке
    var textStartX = marginStart + textWidth + space
    for (i in 1 until texts.size - 1) {
        val text = texts[i]
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                textStartX,
                textY,
                textPaint
            )
        }
        textStartX += textWidth + space
    }
}

private fun NativePaint.getTextHeight(): Float {
    val fm = this.fontMetrics
    return fm.descent - fm.ascent
}