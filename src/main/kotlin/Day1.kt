const val wheelsize = 100

fun main() {
    var position = 50; var part1 = 0; var part2 = 0
    Any::class::class.java.getResourceAsStream("/day1.txt")?.bufferedReader()?.forEachLine { line ->
        (0..line.substring(1, line.length).toInt()).forEach {
            position = (if (line.get(0) == 'L') (position - 1) else (position + 1)).mod(wheelsize)
            if (position == 0) part2++
        }
        if (position == 0) part1++
    }
    println(part1)
    println(part2)
}