package com.obolonnyy.chartapplication.chart

import com.obolonnyy.chartapplication.chart.data.Point
import org.junit.Test

class UtilsKtTest {

    private val testList3 = ArrayList<Point>().apply {
        add(Point(1f, 10f))
        add(Point(10f, 10f))
        add(Point(20f, 10f))
    }

    private val testList = ArrayList<Point>().apply {
        add(Point(0f, 10f))
        add(Point(10f, 10f))
        add(Point(20f, 10f))
        add(Point(30f, 10f))
        add(Point(40f, 10f))
        add(Point(50f, 10f))
        add(Point(60f, 10f))
        add(Point(70f, 10f))
        add(Point(80f, 10f))
        add(Point(90f, 10f))
        add(Point(100f, 10f))
    }

    @Test
    fun foo1() {
        val found = testList.search(2f)
        assert(found.x == testList[0].x)
    }

    @Test
    fun foo2() {
        assert(testList.search(0f).x == testList[0].x)
        assert(testList.search(1f).x == testList[0].x)
        assert(testList.search(2f).x == testList[0].x)
        assert(testList.search(3f).x == testList[0].x)
        assert(testList.search(4f).x == testList[0].x)

        assert(testList.search(7f).x == testList[1].x)
        assert(testList.search(8f).x == testList[1].x)
        assert(testList.search(9f).x == testList[1].x)
        assert(testList.search(10f).x == testList[1].x)
        assert(testList.search(11f).x == testList[1].x)
        assert(testList.search(12f).x == testList[1].x)
        assert(testList.search(13f).x == testList[1].x)
        assert(testList.search(14f).x == testList[1].x)

        assert(testList.search(16f).x == testList[2].x)
        assert(testList.search(17f).x == testList[2].x)
        assert(testList.search(18f).x == testList[2].x)
        assert(testList.search(19f).x == testList[2].x)
        assert(testList.search(25f).x == testList[2].x)
    }
}