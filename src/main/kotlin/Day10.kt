import kotlin.math.abs


fun main() {
    val instructions = Utils.readFileLines("day10.txt")
        .filterNot(String::isBlank)
    fun registerAndCrt(cycle: Int, register: Int): Pair<Int, Char> {
        val crtPosition = cycle %40
        val crt = if (abs(register-crtPosition) <=1 ) '#' else '.'
        return register to crt
    }

    val registerPerCycle = instructions.fold(listOf(1 to '#')) { registerPerCycle, instruction ->
        val split = instruction.split(" ")
        val (last, _) = registerPerCycle.last()
        val cycle = registerPerCycle.size
        when (split[0]) {
            "noop" -> registerPerCycle + registerAndCrt(cycle, last)
            "addx" -> registerPerCycle + registerAndCrt(cycle, last) + registerAndCrt(cycle + 1, last + split[1].toInt())
            else -> error("unknown instruction")
        }
    }

    fun signalStrength(index: Int) = registerPerCycle[index - 1].first * index

    val totalStrength = listOf(20, 60, 100, 140, 180, 220).map(::signalStrength).sum()
    println("Signal strength = $totalStrength")

    registerPerCycle.forEachIndexed { index, (_, crt) ->
        if (index%40 == 0) println()
        print(crt)
    }
}
