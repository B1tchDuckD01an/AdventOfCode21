import java.io.File
import java.nio.file.Path
import java.util.*
import javax.print.attribute.standard.Destination

val numbers = File("input.txt").readLines()

class PointComparator : Comparator<Point> {
    override fun compare(first: Point, second: Point): Int {
        return first.value.compareTo(second.value)
    }
}

data class Point(val coordinate: Pair<Int, Int>, val value: Int) {
    val x = coordinate.first
    val y = coordinate.second
    override fun toString(): String {
        return "$coordinate, $value"
    }
}

fun withinRange(pt: Point, points: MutableList<Point>) = points.filter { listOf(pt.x - 1, pt.x, pt.x + 1).contains(it.x) && it.y == pt.y || listOf(pt.y - 1, pt.y, pt.y + 1).contains(it.y) && it.x == pt.x }
fun withinRangeforArray(pt: Pair<Int, Int>, points: Array<IntArray>): List<Pair<Int, Int>> {
    val list = mutableListOf<Pair<Int, Int>>()
    //add leftmost num unless on edge
    if (pt.first != 0)
        list.add(Pair(pt.first - 1, pt.second))
    //add uppermost num unless on top
    if (pt.second != 0)
        list.add(Pair(pt.first, pt.second - 1))
    //add rightmost num unless on right
    if (pt.first != points[0].size - 1)
        list.add(Pair(pt.first + 1, pt.second))
    //add bottom bum unless on bottom
    if (pt.second != points.size - 1)
        list.add(Pair(pt.first, pt.second + 1))
    return list.toList()
}

fun creategraph(input: MutableList<String>): MutableList<Point> {
    val points = mutableListOf<Point>()
    input.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            points.add(Point(Pair(x, y), Character.getNumericValue(c)))
        }
    }
    return points
}

fun creategraphpart2(input: List<String>): Array<IntArray> {
    fun next(num: Int): Int {
        if (num > 9)
            return num - 9
        return num
    }

    val rows = input.size
    val cols = input[0].length
    val points = Array(rows * 5) { IntArray(cols * 5) }

    for (row in 0 until rows)
        for (col in 0 until cols) {
            var iter = 1
            points[row][col] = input[row][col].digitToInt()

            repeat(4) {
                val nextc = col + (cols * iter)
                val nextr = row + (rows * iter)

                points[row][nextc] = next(points[row][col] + it + 1)
                points[nextr][col] = next(points[row][col] + it + 1)

                var newCounter = 1
                repeat(4) { count ->
                    points[row + (rows * newCounter)][col + (cols * iter)] = next(points[row][nextc] + count + 1)
                    ++newCounter
                }

                ++iter
            }
        }
    return points
}

// graph part 2 was very slow using list
/*
fun creategraphpart2():MutableList<String> {
    fun buildrowdown(rows:MutableList<String>,i:Int):MutableList<String>
    {
        val startrows = rows
        val currentrows = rows
        var newrows = mutableListOf<String>()
        val sb = StringBuilder()
        for(iter in 0..i) {

            for (string in currentrows) {
                for (c in string) {
                    if (c == '9')
                        sb.append("1")
                    else
                        sb.append((Character.getNumericValue(c) + 1).toString())
                }
                newrows.add(sb.toString())
            }
            startrows.addAll(newrows)
            currentrows.clear()
            currentrows.addAll(newrows)
            newrows.clear()
        }
        return startrows
    }

    fun buildrowacross(str:String,i:Int):String
    {
        var string = str
        var sb = StringBuilder()
        var sb2 = StringBuilder()
        for(iter in 0..i) {
            for (c in string) {
                if (c == '9')
                    sb.append("1")
                else
                    sb.append((Character.getNumericValue(c) + 1).toString())
            }
            sb2.append(string)
            sb2.append(sb.toString())
            string = sb.toString()

            sb.clear()
        }
        return sb2.toString()
    }
    var rows = mutableListOf<String>()
    for (string in numbers) {
       rows.add(buildrowacross(string,3))
    }
    rows = buildrowdown(rows,3)
 //   println(rows)
    return rows
}
*/

//dijkstra algorithm for list - slow!
fun path(start: Point, points: MutableList<Point>) {
    //initialise open list
    val openqueue = PriorityQueue(PointComparator())
    // initialise closed list
    val closedqueue = hashSetOf<Pair<Int, Int>>()
    // total value list
    val totalvalue = hashMapOf<Pair<Int, Int>, Int>()

    totalvalue[Pair(0, 0)] = 0

    //add starting node to the list
    openqueue.add(Point(start.coordinate, 0))

    //when the open list is not empty
    while (openqueue.isNotEmpty()) {
        //pop the node with the least value off list (use point comparator)
        val current = openqueue.remove()
        //add to closed q
        closedqueue.add(current.coordinate)

        //if current exists in total with less, move on
        if (totalvalue.getOrDefault(current.coordinate, Int.MAX_VALUE) < current.value) continue

        //generate the successors of current
        for (adjacent in withinRange(current, points)) {
            //if it's already closed, move on
            if (closedqueue.contains(adjacent.coordinate)) continue
            //calculate risk
            val newrisk = totalvalue.getOrDefault(current.coordinate, Int.MAX_VALUE) + adjacent.value
            //if its less, add to total value and add to open queue
            if (newrisk < totalvalue.getOrDefault(adjacent.coordinate, Int.MAX_VALUE)) {
                totalvalue[adjacent.coordinate] = newrisk
                openqueue.add(Point(adjacent.coordinate, newrisk))
            }
        }
    }
    val maxx = points.maxByOrNull { it.coordinate.first }!!.coordinate.first
    val maxy = points.maxByOrNull { it.coordinate.second }!!.coordinate.second

    //path of least risk = risk of max x,maxy
    val to: Int = totalvalue[Pair(maxx, maxy)]!!
    println("total value of path = $to")

}

//path 2 uses array of int array and same algorithm
fun path2(points: Array<IntArray>): Int {
    val openqueue = PriorityQueue(PointComparator())
    val closedqueue = hashSetOf<Pair<Int, Int>>()
    val totalvalue = hashMapOf<Pair<Int, Int>, Int>()
    totalvalue[Pair(0, 0)] = 0
    openqueue.add(Point(Pair(0, 0), 0))
    while (openqueue.isNotEmpty()) {
        val (current, value) = openqueue.remove()
        closedqueue.add(current)
        if (totalvalue.getOrDefault(current, Int.MAX_VALUE) < value) continue
        for (adjacent in withinRangeforArray(current, points)) {
            if (closedqueue.contains(adjacent)) continue
            val newrisk = totalvalue.getOrDefault(current, Int.MAX_VALUE) + points[adjacent.first][adjacent.second]
            if (newrisk < totalvalue.getOrDefault(adjacent, Int.MAX_VALUE)) {
                totalvalue[adjacent] = newrisk
                openqueue.add(Point(adjacent, newrisk))
            }
        }
    }
    return totalvalue[Pair(points.size - 1, points[0].size - 1)]!!
}

fun part1() {
    val points = creategraph(numbers.toMutableList())
    path(points[0], points)
}

fun part2() {
    val points = creategraphpart2(numbers)
    /*
    for(x in 0.. points.size-1) {
        println()

        for (y in 0..points[0].size-1)
            print(points[x][y])
    }
    //   path(points[0],points)*/
    println(path2(points))
}

part2()