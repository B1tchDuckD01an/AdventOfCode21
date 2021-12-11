import java.io.File

val numbers = File("input.txt").readLines().map { it.map { getPartner(it)}}

fun getPartner(c:Char):Symbol{
    if(c == '<')
        return Symbol(c,'>',true,0)
    if(c == '{')
        return Symbol(c,'}',true,0)
    if(c == '(')
        return Symbol(c,')',true,0)
    if(c == '[')
        return Symbol(c,']',true,0)
    if(c == '>')
        return Symbol(c,'<',false,25137)
    if(c == '}')
        return Symbol(c,'{',false,1197)
    if(c == ')')
        return Symbol(c,'(',false,3)
    if(c == ']')
        return Symbol(c,'[',false,57)
    error("problems with $c")
}

//class Chunk(val chunks:MutableList<Chunk>, val symbol:Char)
class Symbol(val c:Char,val p:Char, val open:Boolean,val score:Int){
    override fun toString(): String {
        return "$c "
    }
}

fun part1() {
    val list = mutableListOf<Symbol>()
    var score : Long = 0
    for(s in numbers)
        for(i in 0 .. s.size-1)
        {
            if (s[i].open)
                list.add(s[i])
            else
            {
                if(list.removeLast().p != s[i].c)
                {
                    score += s[i].score
                    break
                }
            }
        }

    println("---- PART1 -----")
    println(score)
}

part1()
/*
    numbers.forEachIndexed { line, list ->
        list.forEachIndexed { i, symbol ->
            if(symbol.open) {
                var chunkindex = 0
                list.forEachIndexed { findindex, symbol ->
                    if (!symbol.open && chunkindex == 0) {
                        chunkindex = findindex
                    }
                }
                println(i+chunkindex-1)
                if(i+chunkindex-1 < list.size) {
                    if (symbol.ispartner(list[i + chunkindex - 1].c)) {
                        println(symbol)
                    } else
                    {
                        if(openlist.filter{ it.first == i}.count() == 0)
                            openlist.add(Pair(i,list[i + chunkindex - 1]))
                    }
                }
            }
        }
    }
println(openlist)

}

part1()

