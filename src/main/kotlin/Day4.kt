fun main() {
    val grid = mutableListOf<MutableList<Char>>()
    Any::class::class.java.getResourceAsStream("/day4.txt")?.bufferedReader()?.forEachLine {
        grid.add(it.toMutableList())
    }
    println(part1(grid))
    println(part2(grid))
}

fun part1(grid: List<List<Char>>): Int {
    var part1 = 0
    grid.forEachIndexed { i, line ->
        line.forEachIndexed { j, _ ->
            if (grid[i][j] == '@' && getRollsAround(i,j,grid) < 4) part1++
        }
    }
    return part1
}

fun part2(grid: List<MutableList<Char>>): Int {
    var part2 = 0
    do {
        var removed = 0
        grid.forEachIndexed { i, line ->
            line.forEachIndexed { j, _ ->
                if (grid[i][j] == '@' && getRollsAround(i,j,grid) < 4) {
                    grid[i][j] = '.'
                    removed++
                    part2++
                }
            }
        }
    } while (removed > 0)
    return part2
}

fun getRollsAround(x: Int, y: Int, map: List<List<Char>>): Int {
    val surrounds = mutableListOf<Char>()
    if (x > 0 && y > 0) surrounds.add(map[x-1][y-1])
    if (x > 0) surrounds.add(map[x-1][y])
    if (x > 0 && y < map[0].size - 1) surrounds.add(map[x-1][y+1])
    if (y > 0) surrounds.add(map[x][y-1])
    if (y < map[0].size - 1) surrounds.add(map[x][y+1])
    if (x < map.size - 1 && y > 0) surrounds.add(map[x+1][y-1])
    if (x < map.size - 1) surrounds.add(map[x+1][y])
    if (x < map.size - 1 && y < map[0].size - 1) surrounds.add(map[x+1][y+1])
    return surrounds.count { it == '@' }
}