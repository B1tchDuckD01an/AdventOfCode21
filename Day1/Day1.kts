import java.io.File

val numbers: List<Int> = File("input.txt").readLines().map { it.toInt()}


fun part1(numbers:List<Int>):Int{
    val numbersIterator = numbers.iterator()
    var count = 0
    var last = numbersIterator.next()

    while(numbersIterator.hasNext()) {
        val current = numbersIterator.next()
        if(last < current) {
            count++
        }
        last = current
    }
    return count
}

fun part2(){
    println(part1(numbers.windowed(3,1).map { it.sum()}))
}

println(part1(numbers))
println(part2())