import java.io.BufferedOutputStream
import java.io.File

val numbers = File("input.txt").readLines().toMutableList()
val balls = numbers.removeFirst()

class Bingo(val x:Int,val y:Int,val number:Int,var marked:Boolean)

class Board(var ball:MutableList<Bingo>)
{

    fun printBingoBoard()
    {
        println("")
        var i = 0
        do {
            for(bingo in ball.filter {it.x == i})
            {
                print(bingo.x)
                print(",")
                print(bingo.y)
                print("   ")
                print(bingo.number)
                print("    ")
            }
            println("")
            i++
        }while(i<=4)
    }

    fun addBingo(bin:Bingo)
    {
        ball.add(bin)
    }

    fun verticalmatch() = ball.groupBy { it.y }.any { (_, value) -> value.all(Bingo::marked) }
    fun horizontalmatch() = ball.groupBy { it.x }.any { (_, value) -> value.all(Bingo::marked) }

    fun haswon() = horizontalmatch() || verticalmatch()

    fun sumofunmarked() = ball.filter { it.marked == false }.sumOf { it.number }
    fun score(value:Int) = sumofunmarked() * value
}

fun createboards(input:MutableList<String>):MutableList<Board>
{
    var currentBoard = Board(mutableListOf<Bingo>())
    val boards = mutableListOf<Board>()
    var iter = 0
    do {
        val taken = input.removeFirst()
        if (taken == "") {
            iter = 0
            if(currentBoard.ball.size > 0) {
                boards.add(currentBoard)
                currentBoard = Board(mutableListOf<Bingo>())
            }
        }
        else
        {
            for(bingo in makebingo(taken,iter))
                currentBoard.addBingo(bingo)
            iter++
        }
    }
    while(input.size != 0)

    boards.add(currentBoard)
    return boards
}

fun makebingo(str:String,iter:Int):MutableList<Bingo> = str.split(" ".toRegex())
        .filterNot(String::isBlank)
        .mapIndexed { index, s -> Bingo(index,iter,s.toInt(),false)}.toMutableList()

fun playbingo(boards:MutableList<Board>,numbers:List<Int>)
{
    for (number in numbers) {
        for (board in boards) {
            for (bingo in board.ball) {
                if (bingo.number == number) {
                    bingo.marked = true
                }

                if (boards.all(Board::haswon)) {
                    print("----- PART2 -----")
                    board.printBingoBoard()
                    println(board.score(number))
                    return
                }
            }
        }
    }
}

fun playbingopart1(boards:MutableList<Board>,numbers:List<Int>):Pair<Int,Board>
{
    for (number in numbers) {
        for (board in boards) {
            for(bingo in board.ball.filter {it.number==number})
            {
                bingo.marked = true
            }
            if(board.haswon())
            {
                return Pair(number, board)
            }
        }
    }

    return Pair(-1,boards[0])
}

fun resetgame(boards:MutableList<Board>)
{
    for (board in boards) {
        for (bingo in board.ball.filter { it.marked == true }) {
            bingo.marked = false
        }
    }
}

fun part1(boards:MutableList<Board>)
{
    println("----- PART1 -------")

    val winningboard = playbingopart1(boards,balls.split(",").map{it.toInt()})
    winningboard.second.printBingoBoard()
    println(winningboard.second.score(winningboard.first))
}

fun part2(boards:MutableList<Board>)
{
    playbingo(boards,balls.split(",").map{it.toInt()})
}

var boards = createboards(numbers)
part1(boards)
resetgame(boards)
part2(boards)