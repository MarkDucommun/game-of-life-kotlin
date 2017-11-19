package game

data class Universe(val cells: Set<Cell>) : Set<Cell> by cells

fun universe(vararg cell: Cell) = Universe(cells = cell.asList().toSet())

fun Universe.next(): Result<Unit, Universe> =
        withRequiredDeadCells
                .let { universe -> universe.map { cell -> cell.nextState(universe) } }
                .filter { it is Alive }
                .toSet()
                .let(::Universe)
                .let(::Success)

fun Universe.createFrame(upperOutermost: Coordinate, origin: Coordinate = Coordinate(x = 0, y = 0)): Result<Unit, Frame> =
        filter { it isBetween origin and upperOutermost }
                .toSet()
                .let { rows -> Frame(rows = rows, origin = origin, upperOutermost = upperOutermost) }
                .let(::Success)

val Universe.withRequiredDeadCells: Universe
    get() = fold(cells) { universe, cell -> universe + cell.missingNeighbors(universe = this) }.let(::Universe)