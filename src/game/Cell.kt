package game

sealed class Cell(val location: Coordinate) {

    override fun equals(other: Any?) = other is Cell && location == other.location

    override fun hashCode() = location.hashCode()

    override fun toString() = location.toString()

    fun toggle(): Cell {
        return when (this) {
            is Alive -> this.kill()
            is Dead -> this.revive()
        }
    }

    companion object {

        fun alive(x: Long, y: Long) = Alive(location = Coordinate(x = x, y = y))

        fun dead(x: Long, y: Long) = Dead(location = Coordinate(x = x, y = y))
    }
}

class Alive(location: Coordinate) : Cell(location = location)

class Dead(location: Coordinate) : Cell(location = location)

fun Cell.livingNeighborCount(cells: Set<Cell>) = livingNeighborCells(cells).size

fun Cell.livingNeighborCells(cells: Set<Cell>) = neighboringCells(cells).filter { it is Alive }.toSet()

fun Cell.neighboringCells(cells: Set<Cell>) = neighborCoordinates.map(cells::find).toSet()

fun Coordinate.asDeadCell() = Dead(location = this)

fun Set<Cell>.find(coordinate: Coordinate) = find { it.location == coordinate } ?: coordinate.asDeadCell()

val Cell.neighborCoordinates: Set<Coordinate>
    get() = setOf(
            location.right,
            location.lowerRight,
            location.lower,
            location.lowerLeft,
            location.left,
            location.upperLeft,
            location.upper,
            location.upperRight
    )

fun Cell.missingNeighbors(universe: Universe): List<Cell> =
        neighborCoordinates
                .filterNot { location -> universe.find { it.location == location } is Cell }
                .map { Dead(location = it) }


fun Cell.nextState(universe: Universe): Cell =
        livingNeighborCount(universe)
                .let { liveNeighborCount ->
                    when (this) {
                        is Dead -> nextState(liveNeighborCount)
                        is Alive -> nextState(liveNeighborCount)
                    }
                }

fun Alive.nextState(liveNeighborCount: Int): Cell =
        if (liveNeighborCount < 2 || liveNeighborCount > 3) kill() else this

fun Dead.nextState(liveNeighborCount: Int): Cell =
        if (liveNeighborCount == 3) revive() else this

fun alive(x: Long, y: Long): Cell = Cell.alive(x = x, y = y)

fun dead(x: Long, y: Long): Cell = Cell.dead(x = x, y = y)

fun Alive.kill() = Dead(location = location)

fun Dead.revive() = Alive(location = location)

infix fun Cell.isBetween(coordinate: Coordinate): CellAndCoordinate {
    return CellAndCoordinate(cell = this, inner = coordinate)
}