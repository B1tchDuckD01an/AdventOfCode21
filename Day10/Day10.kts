import java.io.File

val numbers = File("input.txt").readLines().map { it.map { getPartner(it)}}

fun getPartner(c:Char):Symbol{
    if(c == '<')
        return Symbol(c,'>',true,4)
    if(c == '{')
        return Symbol(c,'}',true,3)
    if(c == '(')
        return Symbol(c,')',true,1)
    if(c == '[')
        return Symbol(c,']',true,2)
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

fun scores() {
    var score: Long = 0
    val allscores = mutableListOf<Long>()
    for (s in numbers) {

        var corrupt = false
        var score2: Long = 0
        val list = mutableListOf<Symbol>()

        for (i in 0..s.size - 1) {
            if (s[i].open)
                list.add(s[i])
            else {
                if (list.removeLast().p != s[i].c) {
                    score += s[i].score
                    corrupt = true
                    break
                }
            }
        }
        if(!corrupt) {
            for (b in list.reversed()) {
                score2 = (score2 * 5) + getPartner(b.c).score
            }
            allscores.add(score2)
        }
    }

    println("---- PART1 -----")
    println(score)

    println("---- PART2 -----")
    println(allscores.sorted()[allscores.size/2])
}

scores()