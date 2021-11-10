package com.obolonnyy.chartapplication.chart

sealed class ChartPressedState {
    object Unpressed : ChartPressedState()
    data class PressOneFinger(val x: Float, val y: Float) : ChartPressedState()
    data class PressTwoFingers(val x1: Float, val y1: Float, val x2: Float, val y2: Float) :
        ChartPressedState()

}