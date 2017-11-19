package game

data class Coordinate(
        val x: Long,
        val y: Long
)

val Coordinate.right: Coordinate get() = copy(x = x + 1)
val Coordinate.left: Coordinate get() = copy(x = x - 1)
val Coordinate.upper: Coordinate get() = copy(y = y + 1)
val Coordinate.lower: Coordinate get() = copy(y = y - 1)
val Coordinate.lowerRight: Coordinate get() = lower.right
val Coordinate.lowerLeft: Coordinate get() = lower.left
val Coordinate.upperRight: Coordinate get() = upper.right
val Coordinate.upperLeft: Coordinate get() = upper.left