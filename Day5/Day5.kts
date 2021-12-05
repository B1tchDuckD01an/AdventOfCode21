import java.io.File

val numbers = File("input.txt").readLines()

class Line(val start:Pair<Int,Int>,val end:Pair<Int,Int>)
{
    fun covershorizontal() = (start.first..end.first)
    fun coversvertical() = (start.second..end.second)
}

//dont judge ugly parsing
fun parsepairs(string: String):Line
{
    val starttoend = string.split(" -> ")
    val start = starttoend.first().split(",")
    val end = starttoend.last().split(",")

    return Line(Pair(start.first().toInt(),start.last().toInt()),Pair(end.first().toInt(),end.last().toInt()))
}

var vals = numbers.map { parsepairs(it)}


