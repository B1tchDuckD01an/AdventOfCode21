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

class Point(val coordinate:Pair<Int,Int>,val value:Int)
{
    val x = coordinate.first
    val y = coordinate.second
    override fun toString(): String {
        return "$coordinate, $value"
    }
}

fun withinRange(pt:Point,points:MutableList<Point>) = points.filter { listOf(pt.x-1,pt.x,pt.x+1).contains(it.x) && it.y == pt.y || listOf(pt.y-1,pt.y,pt.y+1).contains(it.y) && it.x == pt.x}

fun distance(pt:Point,dest:Point) = Math.abs(pt.x- dest.x) + Math.abs(pt.y - dest.y)
fun difficulty(pt:Point, dest:Point) = pt.value + distance(pt,dest)

fun withinAllRange(pt: Point,points: MutableList<Point>) = points.filter { listOf(pt.x - 1, pt.x, pt.x + 1).contains(it.x)
        && listOf(pt.y - 1, pt.y, pt.y + 1).contains(it.y) }

fun creategraph():MutableList<Point>
{
    val points = mutableListOf<Point>()
    numbers.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            points.add(Point(Pair(x, y), Character.getNumericValue(c)))
        }
    }
    return points
}

//dijkstra algorithm
fun path(start: Point,points:MutableList<Point>)
{
    val openqueue = PriorityQueue(PointComparator())
    val closedqueue = hashSetOf<Pair<Int,Int>>()
    val totalvalue = hashMapOf<Pair<Int,Int>,Int>()

    totalvalue[Pair(0,0)] = 0
    openqueue.add(Point(start.coordinate,0))

    while(openqueue.isNotEmpty()) {
        val current = openqueue.remove()
        closedqueue.add(current.coordinate)

        if (totalvalue.getOrDefault(current.coordinate, Int.MAX_VALUE) < current.value) continue

        for (adjacent in withinRange(current,points)) {
            if (closedqueue.contains(adjacent.coordinate)) continue
            val newrisk = totalvalue.getOrDefault(current.coordinate,Int.MAX_VALUE) + adjacent.value
            if(newrisk < totalvalue.getOrDefault(adjacent.coordinate,Int.MAX_VALUE)) {
                totalvalue[adjacent.coordinate] = newrisk
                openqueue.add(Point(adjacent.coordinate ,newrisk))
            }
        }
    }
    val maxx = points.maxByOrNull { it.coordinate.first }!!.coordinate.first
    val maxy = points.maxByOrNull { it.coordinate.second }!!.coordinate.second

    val to :Int = totalvalue[Pair(maxx,maxy)]!!
    println("total value of path = $to")

}
fun part1() {
    val points = creategraph()
    println(path(points[0],points))
}

part1()