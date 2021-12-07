import java.io.File
import java.util.*

val numbers = File("input.txt").readText().split(",").map { it.toInt() }

fun passDay(fish:MutableList<Long>):MutableList<Long> {
    println(fish)
    val newlist = mutableListOf<Long>()
    newlist.addAll(fish)
    Collections.rotate(newlist,-1)
    newlist[6] += fish[0]
    return newlist
}

fun createSea():MutableList<Long> {
    val stages = mutableListOf<Long>()
    for(i in 0..8)
    {
        stages.add(i,numbers.count { it == i }.toLong())
    }
    return stages
}

fun fish() {
    var a = createSea()
    for (i in 1..256) {
        a = passDay(a)
        if(i==80) {
            println("---- PART1 -----")
            println(a.sum())
        }
    }
    println("---- PART2 -----")
    println(a.sum())
}
fish()