package y2025

fun main() {
    var part1 = 0L; var part2 = 0L
    val part1Regex = Regex("(\\w+)\\1")
    val part2Regex = Regex("(\\w+)\\1+")
    Any::class::class.java.getResourceAsStream("/day2.txt")?.bufferedReader()?.readText()?.split(",")?.forEach { text ->
        val parts = text.split("-")
        (parts[0].toLong()..parts[1].toLong()).forEach {i ->
            val string = i.toString()
            if (part1Regex.matches(string)) part1 += i
            if (part2Regex.matches(string)) part2 += i
        }
    }
    println(part1)
    println(part2)
}