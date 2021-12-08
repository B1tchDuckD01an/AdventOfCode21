import java.io.File
val numbers = File("input.txt").readLines()

part1()
part2()

fun parsenumbers(num:List<String>) = num.map { it.split(" | ")}.map { Pair(it[0].split(" "),it[1].split(" "))}

fun part2() {
    var total = 0
    for(item in parsenumbers(numbers))
    {
        val sub = SubNumbers(item.second, item.first)
        total += sub.code
    }
    println("---- PART 2 -----")
    println(total)
}

fun part1(){
    var total = 0
    for(item in parsenumbers(numbers))
    {
        val sub = SubNumbers(item.second, item.first)
        total += sub.code.toString().toList().filter { listOf("1","4","7","8").contains(it.toString())}.count()
    }
    println("---- PART 1 -----")
    println(total)
}

class SubNumbers(val signalentry:List<String>,val signalpattern:List<String>)
{
    val code = decipher(getCipher().toList())

    fun decipher(list:List<Pair<String,Int>>):Int {
        val sb = StringBuilder()
        for (signal in signalentry) {
            for (entry in list) {
                if (entry.first.length == signal.length) {
                    if (entry.first.toList().containsAll(signal.toList()))
                        sb.append(entry.second)
                }
            }
        }
        return sb.toString().toInt()
    }

    //determine signal pattern
    fun getCipher():MutableList<Pair<String,Int>> {
        val thisnumber = mutableListOf<Pair<String, Int>>()

        fun getcode(listofentries:MutableList<Pair<String,Int>>,num:Int):List<Char> =
                listofentries.filter { it.second == num }.first().first.toList()

        fun unknownvalues() = thisnumber.filter { it.second == -1}

        //Find unique numbers first
        signalpattern.forEachIndexed { index, s ->
            if (s.length == 7)
                thisnumber.add(index, Pair(s, 8))
            if (s.length == 2)
                thisnumber.add(index, Pair(s, 1))
            if (s.length == 3)
                thisnumber.add(index, Pair(s, 7))
            if (s.length == 4)
                thisnumber.add(index, Pair(s, 4))
            else
                thisnumber.add(index, Pair(s, -1))
            }

        for(entry in unknownvalues())
        {
            val arr = entry.first.toList()
            val one = getcode(thisnumber, 1)
            val four = getcode(thisnumber, 4)

            // get 2,3,5
            if(arr.size == 5) {
                if(arr.minus(four).count() == 3 && !arr.containsAll(one))
                    thisnumber.add(Pair(entry.first, 2))
                if(arr.minus(four).count() == 2)
                {
                    if(arr.containsAll(one))
                        thisnumber.add(Pair(entry.first, 3))
                    else
                        thisnumber.add(Pair(entry.first, 5))
                }
            }
            //get 6 9 and 0
            if(arr.size == 6)
            {
                if(arr.minus(one).count()==4)
                    if(arr.minus(four).count()==2)
                        thisnumber.add(Pair(entry.first, 9))
                    else
                        thisnumber.add(Pair(entry.first, 0))
                if(arr.minus(one).count()==5)
                    thisnumber.add(Pair(entry.first, 6))
            }
        }
        return thisnumber.filter { it.second != -1}.toMutableList()
    }
}