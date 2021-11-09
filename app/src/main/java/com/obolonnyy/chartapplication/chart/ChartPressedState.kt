package com.obolonnyy.chartapplication.chart

sealed class ChartPressedState {
    object Unpressed : ChartPressedState()
    data class PressOneFinger(val x: Float, val y: Float) : ChartPressedState()
    data class PressTwoFingers(val x1: Int, val y1: Int, val x2: Int, val y2: Int) :
        ChartPressedState()

}