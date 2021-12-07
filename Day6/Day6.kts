import java.io.File
import java.util.*

val numbers = File("input.txt").readText().split(",").map { it.toInt() }

class Sea(var fish:MutableList<Long>) {
    fun passDay() {
        println(fish)

        val newlist = mutableListOf<Long>()
        newlist.addAll(fish)
        Collections.rotate(newlist,-1)
        newlist[6] += fish[0]
        fish = newlist
    }
}

fun createSea():Sea {

    val stages = mutableListOf<Long>()
    for(i in 0..8)
    {
        println("addimg for ")

        stages.add(i,numbers.count { it == i }.toLong())
    }

    return Sea(stages)

}

fun fish() {

    val a = createSea()

    for (i in 1..256) {
            a.passDay()
        if(i==80) {
            println("---- PART1 -----")
            println(a.fish.sum())
        }
    }
    println("---- PART2 -----")
    println(a.fish.sum())
}
fish()