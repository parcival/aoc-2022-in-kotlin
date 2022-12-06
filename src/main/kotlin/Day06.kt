fun main(args: Array<String>) {
    val signal = Utils.readFileLines("day06.txt").filterNot { it.isBlank() }.first()

    tailrec fun findIndexOfMarker(startIndex: Int, endIndex: Int, size: Int): Int {
        if (endIndex > signal.length) return -1
        val duplicateIndex = signal.indexOf(signal[endIndex - 1], startIndex)
        return if (duplicateIndex >= endIndex - 1 || duplicateIndex == -1) {
            if (endIndex - startIndex == size) endIndex else findIndexOfMarker(startIndex, endIndex + 1, size)
        } else findIndexOfMarker(duplicateIndex + 1, endIndex + 1, size)
    }

    val endIndexMarker = findIndexOfMarker(0, 2, 4)
    println("Marker end index: $endIndexMarker")
    val endIndexMessageMarker = findIndexOfMarker(0, 2, 14)
    println("Message marker end index: $endIndexMessageMarker")
}
