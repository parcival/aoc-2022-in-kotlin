class ElfDir(val name: String, val parent: ElfDir? = null) {
    val files = mutableMapOf<String, Int>()
    val dirs = mutableMapOf<String, ElfDir>()
    val size: Int by lazy { files.values.sum() + dirs.values.sumOf(ElfDir::size) }

    companion object {
        val dirs = mutableListOf<ElfDir>()

        fun createDir(name: String, parent: ElfDir? = null): ElfDir = ElfDir(name, parent).also(dirs::add)
    }
}

fun main() {
    val cmdLines = Utils.readFileLines("day07.txt").filterNot { it.isBlank() }
    var currentLineNr = 1
    val rootDir = ElfDir.createDir("/")

    fun parseCmdLines(dir: ElfDir) {
        while (currentLineNr < cmdLines.size) {
            val cmdLine = cmdLines[currentLineNr++]
            when {
                cmdLine == "$ cd .." -> return
                cmdLine.startsWith("$ cd ") -> parseCmdLines(dir.dirs[cmdLine.substring(5)]!!)
                cmdLine.startsWith("dir ") -> ElfDir.createDir(cmdLine.substring(4), dir).also { dir.dirs[it.name] = it }
                cmdLine[0].isDigit() -> {
                    val split = cmdLine.split(" ")
                    dir.files[split[1]] = split[0].toInt()
                }
            }
        }
    }

    parseCmdLines(rootDir)
    println("number of dirs: ${ElfDir.dirs.size}, total size: ${rootDir.size}")
    val smallDirs = ElfDir.dirs.filter { dir -> dir.size < 100000 }
    val sum = smallDirs.sumOf(ElfDir::size)
    println("number of small dirs: ${smallDirs.size}, total size: $sum")
    val spaceLeft = 70000000 - rootDir.size
    val dirToRemove = ElfDir.dirs.sortedBy(ElfDir::size).first { dir -> spaceLeft + dir.size >= 30000000 }
    println("dir to remove ${dirToRemove.name} has size ${dirToRemove.size}, leading to free space of ${spaceLeft + dirToRemove.size}")
}
