package com.obolonnyy.chartapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.composethemeadapter.MdcTheme
import com.obolonnyy.chartapplication.chart.ComposeChart
import com.obolonnyy.chartapplication.chart.data.ChartInputData
import java.time.LocalDateTime


private val transactions2 = mutableListOf<ChartInputData>().apply {
    this.add(ChartInputData(LocalDateTime.now(), 100.0, 1, "123", "123"))
    this.add(ChartInputData(LocalDateTime.now(), 120.0, 1, "123", "123"))
    this.add(ChartInputData(LocalDateTime.now(), 150.0, 1))
    this.add(ChartInputData(LocalDateTime.now(), 120.0, 1))
    this.add(ChartInputData(LocalDateTime.now(), 110.0, 1))
    this.add(ChartInputData(LocalDateTime.now(), 130.0, 1, "123"))
    this.add(ChartInputData(LocalDateTime.now(), 100.0, 1, "123"))
    this.add(ChartInputData(LocalDateTime.now(), 100.0, 1))
}

class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chart = findViewById<ComposeView>(R.id.compose2)
        chart.setContent {
            MdcTheme {
                ComposeChart(
                    transactionsPerSecond = transactions2
                )
            }
        }
    }
}