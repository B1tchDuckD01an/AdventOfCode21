import java.io.File
import java.lang.StringBuilder

val numbers = File("test.txt").readLines()

fun parsepolymer(): Pair<String,MutableList<Pair<String,String>>> {
    var polymer = numbers.first()

    var stringrules = mutableListOf<Pair<String, String>>()
    for (string in numbers) {
        var split = string.split(" -> ")
        if(split.size == 2)
        {
            stringrules.add(Pair(split[0],split[1]))
        }
    }
  //  println(polymer)
  //  println(stringrules)

    return Pair(polymer, stringrules)
}

fun buildpolymerstring(polymer:String,rules:MutableList<Pair<String,String>>):String
{
    var sb = StringBuilder()
    polymer.zipWithNext().forEachIndexed { index, pair ->
        if(rules.map{it.first}.contains(pair.first.toString() + pair.second.toString())) {
            var chartoadd = rules.filter { it.first == pair.first.toString() + pair.second.toString()}.first().second
            sb.append(pair.first)
            sb.append(chartoadd)
            if(index == polymer.length-2)
                sb.append(pair.second)
        }
    }
    println(sb.toString())
    var most = polymer.groupingBy { it }.eachCount()

    println(most)
    return sb.toString()
}

fun rulepairs(rules:MutableList<Pair<String,String>>)=
    rules.fold(mutableMapOf<String,MutableList<String>>(),{ acc, pair ->
        acc.put(pair.first,mutableListOf(pair.first[0].toString() + pair.second, pair.second + pair.first[1].toString()))
        acc
    })

fun generatepolymer(pairs:MutableMap<String, Int>,rules:MutableMap<String,MutableList<String>>):MutableMap<String,Int>
{
   // var counts = mutableMapOf<String, Int>()
    println(pairs)
        for (pair in pairs.toMutableMap().keys) {
            if (rules.keys.contains(pair)) {
                var generatedpairs = rules[pair] ?: mutableListOf()
                for (generatedpair in generatedpairs) {
                    var paircount = pairs.get(generatedpair) ?: 0
                    pairs.put(generatedpair, paircount + 1)
                }
            }
        }
    println(pairs)
    return pairs
}


fun polymerpairs(template:String) =
        template.zipWithNext { a, b -> a.toString() + b.toString()  }

fun part1() {
    var input = parsepolymer()

    //first step
    // var polymer = buildpolymerstring(input.first, input.second)

    val pairs = polymerpairs(input.first).fold(mutableMapOf<String, Int>(), { acc, s ->
        acc.put(s, 1)
        acc
    })
    val rules = rulepairs(input.second)
    var counts = generatepolymer(pairs, rules)
    for (i in 1..40) {
        println("$i cycle")
        counts = generatepolymer(counts, rules)
    }

}


fun part2(){
    var input = parsepolymer()

    //first step
    var polymer = buildpolymerstring(input.first, input.second)

    for(i in 2..40) {
        polymer = buildpolymerstring(polymer,input.second)
    }
    var most = polymer.groupingBy { it }.eachCount().values.maxOrNull() ?: 0
    var least = polymer.groupingBy { it }.eachCount().values.minOrNull() ?: 0
    println( "PART2 : Max - least")
    println(most - least)
}

part1()