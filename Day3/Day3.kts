import java.io.File


val numbers: List<String> = File("input.txt").readLines()

fun part1(input:List<String>)
{
    fun intArray(str:String):List<Int> = str.map{ it.toString().toInt()}

    fun gamma(list:List<Int>):List<Int> = list.map {
        if( it > input.size/2 ) 1
        else 0
    }

    fun epsilon(list:List<Int>):List<Int> = list.map {
        if( it  < input.size/2 ) 1
        else 0
    }
    val inputmap = (0 until input.size).map { i ->
    intArray(input[i])
    }.foldIndexed(mutableListOf<Int>(),{ index, acc, list ->
        list.mapIndexed {
            inx, i ->
                if(index > 0)
                    acc[inx] = acc[inx] + i
                else
                    acc.add(i)
        }
        acc
    }).toList()

    println(gamma(inputmap).joinToString("").toInt(2) * epsilon(inputmap).joinToString("").toInt(2))
    return
}

part1(numbers)
