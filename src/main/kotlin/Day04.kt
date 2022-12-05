import java.lang.Integer.min

private const val NR_OF_SACKS = 3

data class ElfPairSectionAssignment(val content: String) {
    val pairs = content.split(",").map { range ->
        val boundaries = range.split("-")
        boundaries[0].toInt() to boundaries[1].toInt()
    }

    fun overlaps(): Boolean = overlap(pairs[0], pairs[1]) || overlap(pairs[1], pairs[0])
    fun overlapsPartially(): Boolean = overlapsPartial(pairs[0], pairs[1])

    private fun overlap(range1: Pair<Int, Int>, range2: Pair<Int, Int>) =
        range1.first >= range2.first && range1.second <= range2.second
    private fun overlapsPartial(range1: Pair<Int, Int>, range2: Pair<Int, Int>) =
        range1.first <= range2.second && range1.second >= range2.first
}


fun main(args: Array<String>) {
    val pairs = Utils.readFileLines("day04.txt").filterNot { it.isBlank() }.map(::ElfPairSectionAssignment)
    val sumOfPairsThatOverlap = pairs.filter{ it.overlaps() }.size
    println("Nr of pairs that overlap: $sumOfPairsThatOverlap out of ${pairs.size}")
    val sumOfPairsThatPartialOverlap = pairs.filter{ it.overlapsPartially() }.size
    println("Nr of pairs that overlap partially: $sumOfPairsThatPartialOverlap out of ${pairs.size}")
}
