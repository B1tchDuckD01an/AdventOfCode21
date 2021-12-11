import java.io.File

val numbers = File("input.txt").readLines()

class Octo(val x: Int, val y: Int, var energy: Int, var flashed: Boolean)

var octoarray = numbers.mapIndexed { y, s ->
    s.mapIndexed { x, c ->
        Octo(x, y, Character.getNumericValue(c), false)
    }
}.fold(mutableListOf<Octo>(), { acc, list ->
    acc.addAll(list)
    acc
})

fun printoctoarray() {
    for (y in 0..octoarray.map { it.y }.last()) {
        println()
        for (x in 0..octoarray.map { it.x }.last())
            print(octoarray.filter { it.x == x && it.y == y }.first().energy)
    }
    println()
}

fun withinAllRange(pt: Octo) = octoarray.filter { listOf(pt.x - 1, pt.x, pt.x + 1).contains(it.x) && listOf(pt.y - 1, pt.y, pt.y + 1).contains(it.y) }

fun flashingoctopus(pt: Octo) {
    pt.energy = 0
    pt.flashed = true
    val nonflashingoctopus = withinAllRange(pt).filter { !it.flashed }
    nonflashingoctopus.forEach {
        if (!it.flashed)
            it.energy++
        if (it.energy > 9)
            flashingoctopus(it)
    }
}

fun part1() {
    var flashcount = 0
    for (i in 1..100) {
        for (octo in octoarray) {
            octo.flashed = false
            octo.energy++
        }

        for (octo in octoarray) {
            if (octo.energy > 9)
                flashingoctopus(octo)
        }
        flashcount += octoarray.filter { it.energy == 0 }.count()
    }
    println("---- PART 1 -----")
    println(flashcount)
}

fun part2() {
    var i = 0
    var flashcount = 0
    while (flashcount != octoarray.size) {
        i++
        for (octo in octoarray) {
            octo.flashed = false
            octo.energy++
        }
        for (octo in octoarray) {
            if (octo.energy > 9)
                flashingoctopus(octo)
        }
        flashcount = octoarray.filter { it.energy == 0 }.count()
    }

    println("---- PART 2 -----")
    println("synchronised flash on $i ")
}


part2()

