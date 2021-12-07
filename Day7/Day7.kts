import java.io.File
val numbers = File("input.txt").readText().split(",").map { it.toInt() }

fun fuelcost(i:Int,num:Int):Int = if (i > num) (i - num) else (num - i)

fun part1()
{
    var efficientfuel = 999999999
    for (i in 0..numbers.sorted().last()) {
        var fuel = 0
        for (num in numbers) {
            if (fuel > efficientfuel)
                break
            fuel += fuelcost(num,i)
        }
        if (fuel < efficientfuel)
            efficientfuel = fuel
    }
    println("----- PART1 ------")
    println(efficientfuel)
}

fun part2()
{
    var efficientfuel = 999999999
    for (i in 0..numbers.sorted().last()) {
        var fuel = 0
        for (num in numbers) {
            if (fuel > efficientfuel)
                break
            val cost = fuelcost(i,num)
            fuel += cost*(cost+1)/2
        }
        if (fuel < efficientfuel)
            efficientfuel = fuel
    }
    println("----- PART2 ------")
    println(efficientfuel)

}

part1()
part2()