import utils.Point3D

const val LOOPS = 1000

fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/day8.txt")?.bufferedReader()?.readLines()!!
    val distances = mutableListOf<Triple<Point3D, Point3D, Double>>()
    val circuits = mutableListOf<MutableSet<Point3D>>()
    lines.forEachIndexed { i, _ ->
        val junList = lines[i].split(",").map { it.toInt() }
        val junction = Point3D(junList[0], junList[1], junList[2])
        (i + 1..<lines.size).forEach { j ->
            val destinationJunList = lines[j].split(",").map { it.toInt() }
            val destinationJunction = Point3D(destinationJunList[0], destinationJunList[1], destinationJunList[2])
            distances.add(Triple(junction, destinationJunction, junction.distanceTo(destinationJunction)))
        }
        circuits.add(mutableSetOf(junction))
    }
    distances.sortWith { i1, i2 -> i1.third.compareTo(i2.third) }
    distances.forEachIndexed { i, (j1, j2, _) ->
        if (i == LOOPS) println(circuits.map { it.size }.sortedDescending().take(3).reduce { a,b -> a * b }) // part 1 solution
        val set1 = circuits.find { it.contains(j1) }!!
        val set2 = circuits.find { it.contains(j2) }!!
        if (set1 != set2) {
            set1.addAll(set2)
            circuits.remove(set2)
        }
        if (circuits.size == 1) {
            println(j1.x * j2.x) // part 2 solution
            return
        }
    }
}
