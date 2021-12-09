import java.io.File
import java.lang.StringBuilder

val numbers = File("input.txt").readLines().map { it.map { Character.getNumericValue(it)}}

class Point(val x:Int,val y:Int, val value:Int)
{
    var visited = false
    fun basinEdge() = value==9
    fun isLowest(otherpoints:List<Point>) = otherpoints.filter { it.value < this.value }.count() == 0
}

class Heatmap(val points:List<Point>)
{
    fun withinRange(pt:Point) = points.filter { listOf(pt.x-1,pt.x,pt.x+1).contains(it.x) && it.y == pt.y || listOf(pt.y-1,pt.y,pt.y+1).contains(it.y) && it.x == pt.x}
    fun withinAllRange(pt:Point) = points.filter { listOf(pt.x-1,pt.x,pt.x+1).contains(it.x) && listOf(pt.y-1,pt.y,pt.y+1).contains(it.y)}
    fun riskLevel():Int{
        var level = 0
        for(pt in points)
            if(pt.isLowest(withinAllRange(pt)))
                level += 1 + pt.value
        return level
    }
    fun basins() = points.filter { !it.basinEdge() }.fold(mutableListOf<Int>(),{ acc, point ->
            if(!point.visited) {
                acc.add(visitpts(getallptswithinrange(mutableListOf(), point)))
            }
            acc
        })

    fun withinRangeNoEdges(pt:Point) = withinRange(pt).filter{!it.basinEdge()}
    fun getallptswithinrange(inlist:MutableList<Point>, pt: Point):MutableList<Point>
    {
        val list = inlist
        list.addAll(withinRangeNoEdges(pt).filter {!list.contains(it)})
            for (point in withinRangeNoEdges(pt)) {
                if(!list.containsAll(withinRangeNoEdges(point)))
                    getallptswithinrange(list,point)
            }
        return list
    }
    fun visitpts(pts:MutableList<Point>):Int
    {
        for(pt in pts)
        {
            pt.visited = true
        }
        return pts.size
    }
}

fun part1()
{
    println("---- PART1 ----- ")
    val points = numbers.mapIndexed { x, list -> list.mapIndexed { y, c -> Point(x,y,c) } }
            .fold(mutableListOf<Point>(),{acc, list ->
                list.map { acc.add(it)}
                acc
            })
    println(Heatmap(points).riskLevel())
}

fun part2()
{
    println("---- PART2 ----- ")
    val points = numbers.mapIndexed { x, list -> list.mapIndexed { y, c -> Point(x,y,c) } }
            .fold(mutableListOf<Point>(),{acc, list ->
                list.map { acc.add(it)}
                acc
            })
    val ans = Heatmap(points).basins().sortedDescending()

    println(ans)
    println(ans[0] * ans[1] * ans[2])
}

part1()
part2()