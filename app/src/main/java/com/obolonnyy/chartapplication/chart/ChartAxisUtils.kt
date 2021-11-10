package com.obolonnyy.chartapplication.chart

import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

/**
 * рисует горизонтальные пунктирные оси и текст над ними
 */
internal fun DrawScope.drawAxisXDark(data: ChartComputeData, size: Size) {
    this.drawAxisX(data, size, horizontalBrushColorDark)
}

internal fun DrawScope.drawAxisXLight(data: ChartComputeData, size: Size) {
    this.drawAxisX(data, size, horizontalBrushColorLight)
}

private fun DrawScope.drawAxisX(data: ChartComputeData, size: Size, color: Color) {

    // тут можно прописать марджин
    val topMargin = 0.dp.toPx()
    val bottomMargin = 0.dp.toPx()
    val textBottomMargin = 5.dp.toPx()
    val textRightMargin = 16.dp.toPx()
    val lines = 5

    val stepHeight = (size.height - topMargin - bottomMargin) / (lines + 1)
    var startY = size.height - bottomMargin

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 10.dp.toPx()
        this.color = color.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    for (i in 0 until lines) {
        drawLine(
            start = Offset(x = 0f, y = startY),
            end = Offset(x = size.width, y = startY),
            color = color,
            pathEffect = PathEffect.dashPathEffect(listOf(10f, 5f).toFloatArray()),
            strokeWidth = 1.dp.toPx()
        )

        val text = "Text, $startY"
        val textWidth = textPaint.measureText(text)
        val textStartX = size.width - textWidth - textRightMargin

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                textStartX,
                startY - textBottomMargin,
                textPaint
            )
        }

        startY -= stepHeight
    }
}