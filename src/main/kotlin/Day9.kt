import java.awt.Point
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/day9.txt")?.bufferedReader()?.readLines()!!
    val edgeTiles = mutableSetOf<Point>()
    var part1 = 0L
    lines.forEachIndexed { i, _ ->
        val x = lines[i].split(",").map { it.toLong() }
        (i + 1..<lines.size).forEach { j ->
            val compare = lines[j].split(",").map { it.toLong() }
            val area = (abs(x[0] - compare[0]) + 1) * (abs(x[1] - compare[1]) + 1)
            part1 = max(part1, area)
        }
        val next = if (i + 1 > lines.size - 1) 0 else i+1
        val y = lines[next].split(",").map { it.toInt() }
        edgeTiles.addAll(generateRectanglePoints(Point(x[0].toInt(),x[1].toInt()), Point(y[0],y[1])))
    }
    println(part1)
}

fun generateRectanglePoints(p1: Point, p2: Point): List<Point> {
    // Determine the min and max for both x and y coordinates
    val minX = min(p1.x, p2.x)
    val maxX = max(p1.x, p2.x)
    val minY = min(p1.y, p2.y)
    val maxY = max(p1.y, p2.y)
    val points = mutableListOf<Point>()
    (minX..maxX).forEach {x ->
        (minY..maxY).forEach { y -> points.add(Point(x, y)) }
    }
    return points
}

/**
 * Ray-casting algorithm to check if a point is inside a polygon.
 */
fun isPointInsidePolygon(point: Point, polygonSet: Set<Point>): Boolean {
    val polygon = polygonSet.toList()
    var intersections = 0
    if (polygon.contains(point)) return true
    for (i in polygon.indices) {
        val p1 = polygon[i]
        val p2 = polygon[(i + 1) % polygon.size]

        // Check if the ray intersects with the edge
        if ((p1.y > point.y) != (p2.y > point.y)) {
            val xIntersect = p1.x + (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y)
            if (xIntersect > point.x) {
                intersections++
            }
        }
    }
    return intersections % 2 == 1
}

fun drawGrid(map: MutableSet<Point>) {
    (0..20).forEach {x ->
        (0..20).forEach { y ->
            if (map.contains(Point(x,y))) print("#")
            else print(".")
        }
        println()
    }
}