import java.io.File

val numbers = File("input.txt").readLines().toMutableList()

val balls = numbers.removeFirst()
println(balls)

//createboards(numbers)
class Bingo(val x:Int,val y:Int,val number:Int,var marked:Boolean)
{
    fun markball(value:Boolean)
    {
        marked = value
    }
}

class Board(var ball:MutableList<Bingo>, var winner:Boolean)
{
    fun printBingoBoard()
    {
        println("BingoBoard ")
        var i = 0
        do {
            for(bingo in ball.filter {it.x == i})
            {
                print(bingo.x)
                print(",")
                print(bingo.y)
                print(":")
                print(bingo.number)
                print("  ")
            }
            println("")
            i++
        }while(i<=4)

    }

    fun addBingo(bin:Bingo)
    {
        ball.add(bin)
    }

    fun markballs(value:Int)
    {
        for(bingo in ball)
            if(bingo.number == value)
                bingo.markball(true)
                checkwinners()

    }
    fun checkwinners() {
        if ((0 until 4).map { iterator ->
                    ball.filter { it.marked == true }.filter { it.x == iterator }.count() == 5
                }.contains(true))
            winner = true
    }

    fun sumofunmarked() = ball.filter { it.marked == false }.sumOf { it.number }

}

fun createboards(input:MutableList<String>):MutableList<Board>
{
    var iter = 0
    var currentBoard = Board(mutableListOf<Bingo>(),false)
    val boards = mutableListOf<Board>()

    do {
        val taken = input.removeFirst()
        if (taken == "") {
            iter = 0
            if(currentBoard.ball.size > 0)
                boards.add(currentBoard)

            currentBoard = Board(mutableListOf<Bingo>(),false)
        }
        else
        {
            for(bingo in makebingo(taken,iter))
                currentBoard.addBingo(bingo)
            iter++
        }
    }
    while(input.size != 0)

    return boards
}

fun makebingo(str:String,iter:Int):MutableList<Bingo> = str.split("\\s+".toRegex())
        .filter { it != ""}
        .mapIndexed { index, s -> Bingo(index,iter,s.toInt(),false)}.toMutableList()

fun playbingo(boards:MutableList<Board>,numbers:List<Int>):Pair<Int,Board>
{
    for (number in numbers) {
        for (board in boards) {
            board.markballs(number)
            if(board.winner)
            {
                return Pair(number, board)
            }
        }
    }

    return Pair(-1,boards[0])
}

fun part1()
{
    val winningboard = playbingo(createboards(numbers),balls.split(",").map{it.toInt()})
    println("sum of winning is : ")
    print(winningboard.second.sumofunmarked())
    println("number is : ")
    print(winningboard.first)
    println("attempt of an answer : ")
    print(winningboard.first * winningboard.second.sumofunmarked())
}

fun part2()
{



}

part1()