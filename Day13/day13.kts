import java.io.File
val numbers = File("input.txt").readLines()

fun makepoints() {
    val points = mutableListOf<Pair<Int,Int>>()
    val folds = mutableListOf<Pair<String,Int>>()

    fun printOut(pts:MutableList<Pair<Int, Int>>)
    {
        val maxY = pts.map { it.second }.maxOrNull() ?: 0
        val minY = pts.map { it.second }.minOrNull() ?: 0
        for(y in minY .. maxY)
        {
            println()
            for (x in 0..pts.maxOf { it.first })
            {
                if (pts.contains(Pair(x, y))) {
                    print("#")
                }
                else {
                    print(".")
                }
            }
        }
    }
        fun yfold(y:Int,points:MutableList<Pair<Int,Int>>)=
            points.fold(mutableListOf<Pair<Int,Int>>(),{
                acc, pair ->
                if(pair.second > y )
                    acc.add(Pair(pair.first,y-(pair.second-y) ))
                else
                    acc.add(pair)
                acc
            })

        fun xfold(x:Int,points:MutableList<Pair<Int,Int>>)=
            points.fold(mutableListOf<Pair<Int,Int>>(),{
                acc, pair ->
                if(pair.first > x )
                    acc.add(Pair(x+(x-pair.first),pair.second))
                else
                    acc.add(pair)
                acc
            })

    for(string in numbers)
        if(string.contains("fold along")) {
            val split = string.split("fold along ")[1].split("=")
            folds.add(Pair(split[0],split[1].toInt()))
        }
        else if(string.split(",").size == 2) {
            val split = string.split(",")
            points.add(Pair(split[0].toInt(),split[1].toInt()))
        }

    for(fold in folds) {
        if (fold.first == "x") {
            points = xfold(fold.second, points)
        } else {
            points = yfold(fold.second, points)
        }
        printOut(points)
    }
}

makepoints()