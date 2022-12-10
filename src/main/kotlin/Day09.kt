import kotlin.math.abs
import kotlin.math.max

data class RopeState(val positions: List<Position>) {
    data class Position(val x: Int, val y: Int)

    fun move(move: Pair<Int, Int>): RopeState {
        val head = positions.first()
        val newHead = Position(head.x + move.first, head.y + move.second)
        val tail = positions.drop(1)
        val newPositions = tail.fold(listOf(newHead)) {  newPositions, nextPart ->
            val lastPart = newPositions.last()
            val dx = lastPart.x - nextPart.x
            val dy = lastPart.y - nextPart.y
            val newNextPart = if (max(abs(dx), abs(dy)) > 1) {
                Position(lastPart.x - (dx / 2), lastPart.y - (dy / 2))
            } else nextPart
            newPositions + newNextPart
        }

        return RopeState(newPositions)
    }
}

fun main() {
    val moveTypes = mapOf("R" to Pair(1, 0), "L" to Pair(-1, 0), "U" to Pair(0, 1), "D" to Pair(0, -1))
    val moves = Utils.readFileLines("day09.txt")
        .filterNot(String::isBlank).flatMap {
            val split = it.split(" ")
            val count = split[1].toInt()
            val move = moveTypes[split[0]]!!
            List(count) { move }
        }
    val positionsTailsRope2 = tailPositions(moves, 2)
    println("Nr of unique tail positions: ${positionsTailsRope2.size}")
    val positionsTailsRope10 = tailPositions(moves, 10)
    println("Nr of unique tail positions: ${positionsTailsRope10.size}")
}

private fun tailPositions(moves: List<Pair<Int, Int>>, size: Int): List<RopeState.Position> {
    val ropeStates = moves.fold(listOf(RopeState(List(size) { RopeState.Position(0, 0) }))) { states, move ->
        states + states.last().move(move)
    }

    return ropeStates.map { it.positions.last() }.distinct()
}
