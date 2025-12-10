import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver

fun main() {
    val targetRegex = "\\[(.*)] (\\(.*\\)) \\{(.*)}".toRegex()
    var part1 = 0; var part2 = 0
    Loader.loadNativeLibraries()
    val solver = MPSolver.createSolver("SCIP")
    Any::class::class.java.getResourceAsStream("/day10.txt")?.bufferedReader()?.forEachLine { line ->
        val (target, buttons, joltage) = targetRegex.find(line)!!.destructured
        val targetList = target.toCharArray().map { it == '#' }
        val buttonList = buttons.split(" ").map { nums -> nums.split(",").map { it.replace("[^A-Za-z0-9 ]".toRegex(), "").toInt() } }
        val joltageList = joltage.split(",").map { it.toInt() }.toIntArray()
        part1 += minButtonPresses(buttonList, targetList)
        part2 += minButtonPressesILP(buttonList, joltageList, solver)
    }
    println(part1)
    println(part2)
}

fun minButtonPresses(buttons: List<List<Int>>, target: List<Boolean>): Int {
    val buttonVectors = buttons.map { BooleanArray(target.size) { i -> i in it } }
    val targetVector = target.toBooleanArray()
    val n = buttons.size
    var minPresses = Int.MAX_VALUE

    (1 until (1 shl n)).forEach { mask ->
        val result = BooleanArray(target.size)
        var presses = 0
        (0 until n).forEach { i ->
            if ((mask shr i) and 1 == 1) {
                presses++
                target.indices.forEach { j -> result[j] = result[j] xor buttonVectors[i][j] }
            }
        }
        if (result.contentEquals(targetVector)) minPresses = minOf(minPresses, presses)
    }
    return if (minPresses == Int.MAX_VALUE) -1 else minPresses
}

fun minButtonPressesILP(buttons: List<List<Int>>, target: IntArray, solver: MPSolver): Int {
    val n = buttons.size
    val m = target.size

    val x = Array(n) { solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x$it") }

    (0 until m).forEach { i ->
        val ct = solver.makeConstraint(target[i].toDouble(), target[i].toDouble())
        (0 until n).forEach { j -> if (i in buttons[j]) ct.setCoefficient(x[j], 1.0) }
    }

    val objective = solver.objective()
    (0 until n).forEach { j -> objective.setCoefficient(x[j], 1.0) }
    objective.setMinimization()

    val resultStatus = solver.solve()
    return if (resultStatus == MPSolver.ResultStatus.OPTIMAL) x.sumOf { it.solutionValue().toInt() } else -1
}