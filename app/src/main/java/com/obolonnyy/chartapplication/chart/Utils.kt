package com.obolonnyy.chartapplication.chart

// Среди точек List<Point> по координате searchX находим ближайшую точку, и её возвращаем
fun ArrayList<Point>.search(searchX: Float): Point {
    require(this.size > 1)

    val midIndex = this.binarySearch(searchX)
    // мы нашли ближайшую точку midIndex, но это по индексам, а не координатам
    // теперь надо понять, действительно ли она ближайшая, или соседняя будет ближе

    val comparedValue = this[midIndex].x - searchX

    return when {
        // midIndex правее точки searchX
        comparedValue > 0 && midIndex > 0 -> {
            val leftX = this[midIndex - 1].x
            val rightX = this[midIndex].x
            if (searchX - leftX > rightX - searchX) {
                this[midIndex]
            } else {
                this[midIndex - 1]
            }
        }
        // midIndex левуу точки searchX
        comparedValue < 0 && midIndex < this.size - 1 -> {
            val leftX = this[midIndex].x
            val rightX = this[midIndex + 1].x
            if (searchX - leftX > rightX - searchX) {
                this[midIndex + 1]
            } else {
                this[midIndex]
            }
        }
        else -> this[midIndex]
    }
}

private fun ArrayList<Point>.binarySearch(searchX: Float): Int {
    require(this.size > 1)

    var leftIndex = 0
    var rightIndex = this.size - 1
    var midIndex = 0

    while (leftIndex <= rightIndex) {
        midIndex = ((leftIndex + rightIndex) / 2)
        val midVal = this[midIndex].x
        val comparedValue = midVal - searchX

        when {
            comparedValue < 0 -> leftIndex = midIndex + 1
            comparedValue > 0 -> rightIndex = midIndex - 1
            else -> return midIndex
        }
    }
    return midIndex
}

internal fun <T> List<T>.second(): T {
    if (size < 1) throw NoSuchElementException("List has less than 2 elements")
    return this[1]
}