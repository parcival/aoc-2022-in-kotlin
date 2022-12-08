fun main() {
    val grid = Utils.readFileLines("day08.txt")
        .filterNot(String::isBlank).map {
            it.map { char -> char.digitToInt() }
        }
        val gridSize = grid.size
        val treesVisibile = mutableSetOf<Pair<Int, Int>>()

        fun checkTree(i: Int, j: Int, max: Int): Int {
            val treeHeight = grid[i][j]
            return if (treeHeight > max) {
                treesVisibile.add(i to j)
                treeHeight
            } else max
        }

        for (i in 0 until gridSize) {
            var l2r = -1
            var r2l = -1
            var t2b = -1
            var b2t = -1
            for (j in 0 until gridSize) {
                // left to right
                l2r = checkTree(i, j, l2r)
                t2b = checkTree(j, i, t2b)
                val jN = gridSize - 1 - j
                r2l = checkTree(i, jN, r2l)
                b2t = checkTree(jN, i, b2t)
            }
        }
        println("Nr of trees visible: ${treesVisibile.size}")
        fun directionScore(start: Int, diff: Int, blockingTree: (Int) -> Boolean): Int {
            var ref = start + diff
            var score = 1
            while (ref in 0 until gridSize) {
                if (blockingTree(ref)) return score
                ref += diff
                score++
            }
            return score - 1
        }

        fun scenicScore(x: Int, y: Int): Int {
            val treeHouseHeight = grid[x][y]
            val horizontalCheck: (Int) -> Boolean = { ref -> grid[ref][y] >= treeHouseHeight }
            val verticalCheck: (Int) -> Boolean = { ref -> grid[x][ref] >= treeHouseHeight }
            val directionScores = listOf(
                directionScore(x, 1, horizontalCheck),
                directionScore(x, -1, horizontalCheck),
                directionScore(y, 1, verticalCheck),
                directionScore(y, -1, verticalCheck))

            return directionScores.reduce { product, i -> product * i  }
        }

        val maxScore = (1 until gridSize-1).flatMap { i ->
            (1 until gridSize - 1).map { j ->
                scenicScore(i, j)
            }
        }.max()
        println("The max score is: $maxScore")
    }
