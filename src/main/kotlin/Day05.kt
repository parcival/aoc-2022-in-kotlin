data class StackMove(val amount: Int, val from: Int, val to: Int)

fun main(args: Array<String>) {
    val lines = Utils.readFileLines("day05.txt").filterNot { it.isBlank() }
    val startOfMovesIndex = lines.indexOfFirst { it.startsWith("move") }
    val stackLines = lines.subList(0, startOfMovesIndex - 1).reversed()

    val movesRegex = """move (\d*) from (\d*) to (\d*)""".toRegex()
    val moves = lines.drop(startOfMovesIndex).map {
        val (amount, from, to) = movesRegex.find(it)?.destructured ?: error("instruction error")
        StackMove(amount.toInt(), from.toInt() - 1, to.toInt() - 1)
    }
    val nrOfStacks = (stackLines[0].length + 1) / 4
    println("Nr of moves: ${moves.size}, nr of stacks: $nrOfStacks")

    val stacks = createStacks(nrOfStacks, stackLines)

    moves.forEach { move ->
        (0 until move.amount).forEach {
            stacks[move.to].add(stacks[move.from].removeLast())
        }
    }

    val code = elfCode(stacks)
    println("Elf code: $code")

    val stacks2 = createStacks(nrOfStacks, stackLines)

    moves.forEach { move ->
        val fromStack = stacks2[move.from]
        stacks2[move.to].addAll(fromStack.takeLast(move.amount))
        for(i in 1..move.amount) stacks2[move.from].removeLast()
    }
    val code2 = elfCode(stacks2)
    println("Elf code: $code2")
}

private fun elfCode(stacks: Array<ArrayDeque<Char>>): String {
    return String(stacks.map { it.last() }.toCharArray())
}

private fun createStacks(
    nrOfStacks: Int,
    stackLines: List<String>
): Array<ArrayDeque<Char>> {
    val stacks = Array(nrOfStacks) { ArrayDeque<Char>() }
    stackLines.forEach { stackLine ->
        (0 until nrOfStacks).forEach {
            val stackValue = stackLine[it * 4 + 1]
            if (stackValue != ' ') stacks[it].add(stackValue)
        }
    }
    return stacks
}
