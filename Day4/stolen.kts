import java.io.File
val numbers = File("input.txt").readLines()
println(part2(numbers))

    fun part1(input: List<String>): Int {
        val winningNumbers = input.first().split(",").map(String::toInt)
        val boards = mutableListOf<Board>()
        val boardsNumbers = input.subList(2, input.size).flatMap {
            it.split(" ").filterNot(String::isBlank).map(String::toInt)
        }.toList()

        val numberOfBoards = boardsNumbers.size / 25
        repeat(numberOfBoards) { boardIndex ->
            val numbers = mutableListOf<Number>()
            repeat(5) { rowIndex ->
                repeat(5) { columnIndex ->
                    val numberIndex = 25 * boardIndex + 5 * rowIndex + columnIndex
                    numbers.add(Number(boardsNumbers[numberIndex], rowIndex, columnIndex))
                }
            }
            boards.add(Board(numbers))
        }

        for (drawnNumber in winningNumbers) {
            for (board in boards) {
                for (number in board.numbers) {
                    if (number.value == drawnNumber) {
                        number.drawn = true
                        if (board.hasWon()) {
                            return board.numbers.filter { it.drawn.not() }.sumOf(Number::value) * drawnNumber
                        }
                    }
                }

            }
        }

        error("No board has won.")
    }

    fun part2(input: List<String>): Int {
        val winningNumbers = input.first().split(",").map(String::toInt)
        val boards = mutableListOf<Board>()
        val boardsNumbers = input.subList(2, input.size).flatMap {
            it.split(" ").filterNot(String::isBlank).map(String::toInt)
        }.toList()

        val numberOfBoards = boardsNumbers.size / 25
        repeat(numberOfBoards) { boardIndex ->
            val numbers = mutableListOf<Number>()
            repeat(5) { rowIndex ->
                repeat(5) { columnIndex ->
                    val numberIndex = 25 * boardIndex + 5 * rowIndex + columnIndex
                    numbers.add(Number(boardsNumbers[numberIndex], rowIndex, columnIndex))
                }
            }
            boards.add(Board(numbers))
        }

        for (drawnNumber in winningNumbers) {
            for (board in boards) {
                for (number in board.ball) {
                    if (number.value == drawnNumber) {
                        number.drawn = true
                        print("count of winning boards : ")
                        println(boards.filter(Board::hasWon).count())
                        if (boards.all(Board::hasWon)) {
                            return board.numbers.filter { it.drawn.not() }.sumOf(Number::value) * drawnNumber
                        }
                    }
                }

            }
        }

        error("No board has won.")
    }


private data class Board(
        val numbers: MutableList<Number>
) {
    fun hasWon() = hasCompleteRow() || hasCompleteColumn()
    fun hasCompleteRow() = numbers.groupBy { it.x }.any { (_, value) -> value.all(Number::drawn) }
    fun hasCompleteColumn() = numbers.groupBy { it.y }.any { (_, value) -> value.all(Number::drawn) }
}

private data class Number(val value: Int, val x: Int, val y: Int, var drawn: Boolean = false)