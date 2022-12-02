private const val i = 3

fun main(args: Array<String>) {
    val guide = Utils.readFileLines("day02.txt").filterNot{ it.isBlank() }.map { it[0] to it[2] }
    val shapeScore = mapOf('A' to 1, 'B' to 2, 'C' to 3, 'X' to 1, 'Y' to 2, 'Z' to 3)
    val gameScore = mapOf(
        ('A' to 'X') to (3 to 3),
        ('A' to 'Y') to (0 to 6),
        ('A' to 'Z') to (6 to 0),
        ('B' to 'X') to (6 to 0),
        ('B' to 'Y') to (3 to 3),
        ('B' to 'Z') to (0 to 6),
        ('C' to 'X') to (0 to 6),
        ('C' to 'Y') to (6 to 0),
        ('C' to 'Z') to (3 to 3),
    )
    val outComeGame = mapOf(
        ('A' to 'X') to 'Z',
        ('A' to 'Y') to 'X',
        ('A' to 'Z') to 'Y',
        ('B' to 'X') to 'X',
        ('B' to 'Y') to 'Y',
        ('B' to 'Z') to 'Z',
        ('C' to 'X') to 'Y',
        ('C' to 'Y') to 'Z',
        ('C' to 'Z') to 'X',
    )

    val (totalScoreElf, totalScoreYou) = playRounds(guide, shapeScore, gameScore)
    println("Elf total score: $totalScoreElf\tYour score: $totalScoreYou")
    val (totalScoreElf2, totalScoreYou2) = playRounds(guide, shapeScore, gameScore) { game ->
        val (elfShape, _) = game
        elfShape to outComeGame[game]!!
    }
    println("Elf total score: $totalScoreElf2\tYour score: $totalScoreYou2")
}

private fun playRounds(
    guide: List<Pair<Char, Char>>,
    shapeScore: Map<Char, Int>,
    gameScore: Map<Pair<Char, Char>, Pair<Int, Int>>,
    gameToPlay: (Pair<Char,Char>) -> Pair<Char, Char> = { it }
): Pair<Int, Int> {
    val totalScore = guide.fold(0 to 0) { (elfTotalScore, yourTotalScore), game ->
        val realGame = gameToPlay(game)
        val (elfShape, yourShape) = realGame
        val elfScore = shapeScore[elfShape]!! + gameScore[realGame]!!.first
        val yourScore = shapeScore[yourShape]!! + gameScore[realGame]!!.second

        (elfTotalScore + elfScore) to (yourTotalScore + yourScore)
    }
    return totalScore
}
