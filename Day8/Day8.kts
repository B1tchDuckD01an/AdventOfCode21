import java.io.File
val numbers = File("input.txt").readLines()
fun parsenumbers(num:List<String>) = num.map { it.split(" | ")}.map { Pair(it[0].split(" "),it[1].split(" "))}

class SubNumbers(val signalentry:List<String>,val signalpattern:List<String>)
{
    var cipher = getpatterns(getcommonnumber())
    var code = decipher(cipher.toList())

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
    fun getcommonnumber():MutableList<Pair<String,Int>> {
        val thisnumber = mutableListOf<Pair<String, Int>>()

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
        return thisnumber
    }

        fun getcode(listofentries:MutableList<Pair<String,Int>>,listofnumbers:List<Int>):MutableList<Char> = listofentries
                .filter { listofnumbers.contains(it.second) }
            .map { it.first.toCharArray() }
            .fold(mutableListOf<Char>(),{ acc, chars ->
                for (char in chars)
                    if(!acc.contains(char))
                        acc.add(char)
                acc
            })

    fun getpatterns(thisnumber:MutableList<Pair<String, Int>>):List<Pair<String,Int>>
    {
        fun unknownvalues() = thisnumber.filter { it.second == -1}

        for(entry in unknownvalues())
        {
            val arr = entry.first.toList()
            val one = getcode(thisnumber, listOf(1))
            val four = getcode(thisnumber, listOf(4))

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
        return thisnumber.filter { it.second != -1}
    }
}

fun createsubnumbers(thelist: List<Pair<List<String>,List<String>>>){
    val sub = mutableListOf<SubNumbers>()
    for(item in thelist)
    {
        sub.add(SubNumbers(item.second, item.first))
    }
    var total = 0
    for (subNumbers in sub) {
        subNumbers.getcommonnumber()
        total += subNumbers.code
    }
    println("---- PART 2 -----")

    println(total)
}

fun part2() {
    val listofpatterns = parsenumbers(numbers)
    createsubnumbers(listofpatterns)
}

fun part1(){
    val listofpatterns = parsenumbers(numbers)
    val sub = mutableListOf<SubNumbers>()
    for(item in listofpatterns)
    {
        sub.add(SubNumbers(item.second, item.first))
    }
    var total = 0
    for (subNumbers in sub) {
        subNumbers.getcommonnumber()
        total += subNumbers.code.toString().toList().filter { listOf("1","4","7","8").contains(it.toString())}.count()
    }
    println("---- PART 1 -----")
    println(total)
}

part1()
part2()