private const val NR_OF_SACKS = 3

data class RuckSack(val content: String) {
    private val middle = content.length / 2
    private val left = content.substring(0 until middle)
    private val right = content.substring(middle)
    val common = left.first { itemType ->
        right.contains(itemType)
    }
}

data class RuckSackGroup(val ruckSacks: List<RuckSack>) {
    val badge: Char =
        ruckSacks[0].content.first { itemType ->
            ruckSacks.all { ruckSack ->
                ruckSack.content.contains(itemType)
            }
        }
}

fun priority(itemType: Char): Int =
    when (itemType) {
        in 'a'..'z' -> 1 + itemType.minus('a')
        else -> 27 + itemType.minus('A')
    }

fun main(args: Array<String>) {
    val ruckSacks = Utils.readFileLines("day03.txt").filterNot { it.isBlank() }.map(::RuckSack)
    val sumOfPriorities = ruckSacks.sumOf { ruckSack -> priority(ruckSack.common) }
    println("Sum of priorities: $sumOfPriorities")

    val ruckSackLists: List<List<RuckSack>> = ruckSacks.foldIndexed(emptyList()) { index, ruckSackGroups, ruckSack ->
        if (index % NR_OF_SACKS == 0) {
            ruckSackGroups.plus<List<RuckSack>>(listOf(ruckSack))
        } else {
            val newRuckSackGroup: List<RuckSack> = ruckSackGroups.last() + ruckSack
            ruckSackGroups.dropLast(1).plus<List<RuckSack>>(newRuckSackGroup)
        }
    }
    val ruckSackGroups = ruckSackLists.map(::RuckSackGroup)
    val sumBadgeTypePriorities = ruckSackGroups.sumOf { ruckSackGroup -> priority(ruckSackGroup.badge)}
    println("Sum of priorities: $sumBadgeTypePriorities")
}
