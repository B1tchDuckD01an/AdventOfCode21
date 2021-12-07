import java.io.File
import java.util.*

val numbers = File("input.txt").readLines()

data class Point(val x:Int,val y:Int){
    fun isHorizontallyAligned(pt:Point) = x == pt.x
    fun isVerticaallyAligned(pt:Point) = y == pt.y
}

data class Line(val start:Point,val end:Point){
    fun getLinePoints():MutableList<Point>{
        if(start.isHorizontallyAligned(end))
            return createHorizontalLine()
        if(start.isVerticaallyAligned(end))
            return createVerticalLine()
        else return createDiagonalLine()
    }

    fun createHorizontalLine():MutableList<Point> {
        //x is the same
        val points = mutableListOf<Point>()
        if(start.y <= end.y) {
            for (i in start.y..end.y)
                points.add(Point(start.x,i))
        }
        else
        {
            for (i in start.y downTo end.y)
                points.add(Point(start.x, i))
        }
        return points
    }

    fun createVerticalLine():MutableList<Point> {
        //y is the same
        val points = mutableListOf<Point>()
        if(start.x <= end.x) {
            for (i in start.x..end.x)
                points.add(Point(i, start.y))
        }
        else {
            for (i in start.x downTo end.x)
                points.add(Point(i, start.y))
        }
        return points
    }

    fun createDiagonalLine():MutableList<Point> {
        fun getLine():MutableList<Point> {
            val points = mutableListOf<Point>()
            var x = start.x
            var y = start.y
            while (x <= end.x && y <= end.y) {
                points.add(Point(x, y))
                x++
                y++
            }
            return points
        }
        fun getLineyDown():MutableList<Point> {
            val points = mutableListOf<Point>()
            var x = start.x
            var y = start.y
            while (x <= end.x && y >= end.y){
                points.add(Point(x, y))
                x++
                y--
            }
            return points
        }
        fun getLinexDown():MutableList<Point> {
            val points = mutableListOf<Point>()
            var x = start.x
            var y = start.y
            do {
                points.add(Point(x, y))
                x--
                y++
            } while (x >= end.x && y <= end.y)
            return points
        }
        fun getLineDown():MutableList<Point> {
            val points = mutableListOf<Point>()
            var x = start.x
            var y = start.y
            while (x >= end.x && y >= end.y){
                points.add(Point(x, y))
                x--
                y--
            }
            return points
        }

        if (start.x <= end.x && start.y <= end.y) {
            return getLine()
        }
        if (start.x <= end.x && start.y >= end.y) {
            return getLineyDown()
        }
        if (start.x >= end.x && start.y <= end.y) {
            return getLinexDown()
        }
        return getLineDown()
    }
}

fun parsepairs(strings: List<String>):MutableList<Line> {
    val lines = mutableListOf<Line>()

    for (string in strings) {
        val starttoend = string.split(" -> ")
        val start = starttoend.first().split(",")
        val end = starttoend.last().split(",")

        lines.add(Line(Point(start.first().toInt(), start.last().toInt()), Point(end.first().toInt(), end.last().toInt())))
    }

    return lines
}

fun countintersectinglines(lines:MutableList<Line>):Int{
    val map = mutableMapOf<Point,Int>()
        for(line in lines)
        {
            for(point in line.getLinePoints())
                map[point] = map[point]?.inc() ?: 1
        }
    return map.filter { it.value > 1 }.count()
}

var vals = parsepairs(numbers)
println("---- PART1 -----")
println(countintersectinglines(vals.filter { it.start.isVerticaallyAligned(it.end) || it.start.isHorizontallyAligned(it.end)}.toMutableList()))

println("---- PART2 -----")
println(countintersectinglines(vals))


