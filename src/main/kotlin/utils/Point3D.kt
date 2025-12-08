package utils

import kotlin.math.sqrt

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point3D): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
}