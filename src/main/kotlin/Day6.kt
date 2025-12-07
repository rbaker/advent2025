fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/day6.txt")?.bufferedReader()?.readLines()!!
    val numOperands = lines.size - 1
    val grid = mutableListOf<Triple<MutableList<Long>, MutableList<Long>, Char>>()
    val operators = lines[numOperands].replace(" ", "")
    lines[0].trim().split("\\s+".toRegex()).forEachIndexed { i, it -> grid.add(Triple(mutableListOf(it.toLong()), mutableListOf(), operators[i])) }
    (1..<numOperands).forEach {i -> lines[i].trim().split("\\s+".toRegex()).forEachIndexed{idx, it -> grid[idx].first.add(it.trim().toLong()) } }
    var workingGrid = 0
    (0..<lines[0].length).forEach { idx ->
        var vertical = ""
        (0..<numOperands).forEach {
            vertical += lines[it][idx]
        }
        if (vertical.isNotBlank()) grid[workingGrid].second.add(vertical.trim().toLong())
        else workingGrid++
    }
    println(grid.sumOf { it.first.reduce{ a, b -> if (it.third == '+') a + b else a * b }  }) //part 1
    println(grid.sumOf { it.second.reduce{ a, b -> if (it.third == '+') a + b else a * b }  }) // part 2
}