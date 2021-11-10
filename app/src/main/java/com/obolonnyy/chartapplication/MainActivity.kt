package com.obolonnyy.chartapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.composethemeadapter.MdcTheme
import com.obolonnyy.chartapplication.chart.ComposeChart
import com.obolonnyy.chartapplication.chart.data.TransactionRate
import com.obolonnyy.chartapplication.chart.data.TransactionsPerSecond
import kotlin.random.Random


private val random = Random(4)

//private val transactions = (0..20).toList().map {
//    TransactionRate(it.toLong(), random.nextDouble())
//}.toMutableList().apply {
//    this.add(TransactionRate(21, 0.0))
//}

private val transactions = mutableListOf<TransactionRate>().apply {
    this.add(TransactionRate(1, 110.0))
    this.add(TransactionRate(1, 120.0))
    this.add(TransactionRate(1, 130.0))
    this.add(TransactionRate(1, 135.0))
    this.add(TransactionRate(1, 100.0))
    this.add(TransactionRate(1, 120.0))
    this.add(TransactionRate(1, 115.0))
    this.add(TransactionRate(1, 110.0))
    this.add(TransactionRate(1, 150.0))
    this.add(TransactionRate(1, 110.0))
}

//private val transactions = mutableListOf<TransactionRate>().apply {
//    this.add(TransactionRate(1, 100.0))
//    this.add(TransactionRate(1, 150.0))
//    this.add(TransactionRate(1, 100.0))
//    this.add(TransactionRate(1, 150.0))
//    this.add(TransactionRate(1, 100.0))
//}

private val transactionsPerSecond = TransactionsPerSecond(transactions)

class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chart = findViewById<ComposeView>(R.id.compose2)
        chart.setContent { MdcTheme { ComposeChart(
            transactionsPerSecond = transactionsPerSecond
        ) } }
    }
}