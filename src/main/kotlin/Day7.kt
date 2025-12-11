import utils.BinaryTreeNode
import java.awt.Point

fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/day7.txt")?.bufferedReader()?.readLines()!!
    println(part1(lines))
    println(part2(lines))
}

fun part1(map: List<String>): Int {
    var part1 = 0
    val tachyonLocations = mutableListOf<Point>()
    map.forEachIndexed { l, _ ->
        map[l].toCharArray().forEachIndexed { i, ch ->
            if (ch == 'S' || (ch == '.' && tachyonLocations.contains(Point(l-1, i)))) tachyonLocations.add(Point(l,i))
            if (ch == '^' && tachyonLocations.contains(Point(l-1, i))) {
                tachyonLocations.add(Point(l, i-1))
                tachyonLocations.add(Point(l, i+1))
                part1++
            }
        }
    }
    return part1
}

fun part2(map: List<String>): Long {
    val splitters = mutableListOf<BinaryTreeNode<Point>>()
    map.forEachIndexed { l, line ->
        line.toCharArray().forEachIndexed { i, ch -> if (ch == '^') splitters.add(BinaryTreeNode(Point(l,i))) }
    }
    splitters.add(BinaryTreeNode(Point(-1,-1)))
    splitters.forEach {
        if (it.value.x != -1) {
            val left = nextSplitterInCol(map, Point(it.value.x, it.value.y - 1))
            val right = nextSplitterInCol(map, Point(it.value.x, it.value.y + 1))
            it.left = splitters.find { i -> i.value == left }
            it.right = splitters.find { i -> i.value == right }
        }
    }
    val memo = mutableMapOf<BinaryTreeNode<Point>, Long>()
    return countRootToLeafPaths(splitters[0], memo)
}

fun nextSplitterInCol(map: List<String>, point: Point): Point {
    (point.x..<map.size).forEach { if (map[it][point.y] == '^') return Point(it, point.y) }
    return Point(-1,-1)
}
fun countRootToLeafPaths(node: BinaryTreeNode<Point>?, memo: MutableMap<BinaryTreeNode<Point>, Long>): Long {
    if (node == null) return 0
    memo[node]?.let { return it }
    if (node.left == null && node.right == null) return 1
    val totalPaths = countRootToLeafPaths(node.left, memo) + countRootToLeafPaths(node.right, memo)
    memo[node] = totalPaths
    return totalPaths
}

