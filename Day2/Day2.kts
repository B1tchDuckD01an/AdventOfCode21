import java.io.File

val numbers = File("input.txt").readLines().map {
    it.split(" ")
}.map {
    Pair(it[0],it[1].toInt())
}

fun part2(input:List<Pair<String,Int>>){
    fun traverse(position:Pair<Int,Int>,aimin:Int,command:Pair<String,Int>):Pair<Pair<Int,Int>,Int> {
        var depth = position.second
        var horizontal = position.first
        var aim = aimin
        if (command.first == "forward")
        {
            horizontal = position.first + command.second
            depth = depth + (aim * command.second)
            }
        if (command.first == "down") {
           // depth = position.second + command.second
            aim = aim + command.second
        }
        if (command.first == "up") {
           // depth = position.second - command.second
            aim = aim - command.second
        }
        return Pair(Pair(horizontal,depth),aim)
    }
    var position = Pair(0,0)
    var aim = 0
    input.forEach { val result = traverse(position,aim,it)
    position = result.first
    aim = result.second }
    println(position.first * position.second)
}

fun part1(input:List<Pair<String,Int>>)
{
    fun traverse(position:Pair<Int,Int>,command:Pair<String,Int>):Pair<Int,Int> {
        var depth = position.second
        var horizontal = position.first
        if (command.first == "forward")
        {
            horizontal = position.first + command.second
        }
        if (command.first == "down") {
             depth = position.second + command.second
        }
        if (command.first == "up") {
             depth = position.second - command.second
        }
        return Pair(horizontal,depth)
    }
    var position = Pair(0,0)
    input.forEach { position = traverse(position,it)}
    println(position.first * position.second)
}

part1(numbers)
part2(numbers)

