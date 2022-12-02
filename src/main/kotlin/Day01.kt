private const val i = 3

fun main(args: Array<String>) {
    val elfLines = Utils.readFileLines("day01.txt")
    val elfTotals = elfLines.fold(emptyList<Pair<Int, Int>>()) { elfCalories, next ->
        if (elfCalories.isEmpty()) return@fold listOf(1 to next.toInt())
        val (elf, calories) = elfCalories.last()
        if (next.isEmpty()) {
            val nextElf = elf + 1
            elfCalories + (nextElf to 0)
        } else {
            val newCalories = calories + next.toInt()
            elfCalories.dropLast(1) + (elf to newCalories)
        }
    }
    val sortedElfTotals = elfTotals.sortedBy { (_, calories) -> -calories }
    val (elfNr, calories) = sortedElfTotals.first()
    println("Elf $elfNr carries the most calories: $calories")

    val topThree = sortedElfTotals.subList(0, 3)
    val totalCalories = topThree.sumOf { (_, calories) -> calories }

    println("Total calories carried by ${topThree.joinToString()} is $totalCalories")
}
