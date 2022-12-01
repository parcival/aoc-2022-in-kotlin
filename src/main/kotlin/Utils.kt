object Utils {
    fun readFileLines(path: String) = String(Utils::class.java.getResourceAsStream(path).readBytes()).lines()
}
