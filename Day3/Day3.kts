import java.io.File
import java.lang.StringBuilder

val numbers = File("test.txt").readLines().map { intArray(it) }
/*
fun gamma(list:List<Int>,size:Int):List<Int> = list.map {
    if( it >= Math.abs(size/2) ) 1
    else 0
}

fun epsilon(list:List<Int>,size:Int):List<Int> = list.map {
    if( it  <= Math.abs(size/2 )) 1
    else 0
}
*/

fun intArray(str:String):List<Int> = str.map{ it.toString().toInt()}
/*
fun recurse(listin:List<List<Int>>,a:Boolean):List<Int> {
    fun iterate(iter: Int, input: List<List<Int>>, compare: List<Int>,boolean: Boolean): List<List<Int>> = input.filter() { it -> it[iter] != compare[iter]}

    var list = listin
    var iter = 0
    do {
        if(a==true)
            list = iterate(iter, list, epsilon(countof1(list), list.size),a)
        else
            list = iterate(iter, list, gamma(countof1(list), list.size),a)
        iter++
    }while(list.size > 1)
    return list[0]
}
*/
fun newpart1()
{

    var commonlist = mutableListOf<String>()
    for(i in 0 .. numbers[0].size-1) {
        var binarylist = mutableListOf<Int>()
        var sb = StringBuilder()

        for (each in 0..numbers.size-1) {
            binarylist.add(numbers[each][i])
        }
       for(int in binarylist) {
           sb.append(int.toString())
       }
        commonlist.add(sb.toString())
        
    }
    println(commonlist)
}

newpart1()

/*
fun part2(input:List<List<Int>>)
{
    println("O2 :")
   println(recurse(input,true).joinToString("").toInt(2))
    println("Scrubber :")
    println(recurse(input,false).joinToString("").toInt(2))
}

fun part1(input:List<List<Int>>)
{
    println(gamma(countof1(input),input.size).joinToString("").toInt(2)
            * epsilon(countof1(input),input.size).joinToString("").toInt(2))
}

fun countof1(input:List<List<Int>>):List<Int> = input.foldIndexed(mutableListOf<Int>(),{ index, acc, list ->
    list.mapIndexed {
        inx, i ->
        if(index > 0)
            acc[inx] = acc[inx] + i
        else
            acc.add(i)
    }
    acc
}).toList()

//part1(numbers)
//part2(numbers)

newpart1()