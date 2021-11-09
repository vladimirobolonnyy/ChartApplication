package com.obolonnyy.chartapplication.chart

// Среди точек List<Point> по координате searchX находим ближайшую точку, и её возвращаем
fun ArrayList<Point>.search(searchX: Float): Point {
    require(this.size > 1)

    var low = 0
    var high = this.size - 1
    var midIndex = 0
    var cmp = 0f

    while (low <= high) {
        midIndex = ((low + high) / 2)
        val midVal = this[midIndex].x
        cmp = midVal - searchX

        when {
            cmp < 0 -> low = midIndex + 1
            cmp > 0 -> high = midIndex - 1
            else -> return this[midIndex]
        }
    }

//    val midIndex = this.binarySearch(searchX)

    // мы нашли ближайшую точку midIndex, но это по индексам, а не координатам
    // теперь надо понять, действительно ли она ближайшая, или соседняя будет ближе


    return when {
        // midIndex правее точки searchX
        cmp > 0 && midIndex > 0 -> {
            val leftX = this[midIndex - 1].x
            val rightX = this[midIndex].x
            if (searchX - leftX > rightX - searchX) {
                this[midIndex]
            } else {
                this[midIndex - 1]
            }
        }
        // midIndex левуу точки searchX
        cmp < 0 && midIndex < this.size - 1 -> {
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

fun ArrayList<Point>.binarySearch(searchX: Float): Int {
    require(this.size > 1)

    var low = 0
    var high = this.size - 1
    var midIndex = 0

    while (low <= high) {
        midIndex = ((low + high) / 2)
        val midVal = this[midIndex].x
        val cmp = midVal - searchX

        when {
            cmp < 0 -> low = midIndex + 1
            cmp > 0 -> high = midIndex - 1
            else -> return midIndex
        }
    }
    return midIndex
}