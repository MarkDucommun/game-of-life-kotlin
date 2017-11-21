package game

data class Universe(val cells: Set<Cell> = emptySet()) : Set<Cell> by cells

fun universe(vararg cell: Cell) = Universe(cells = cell.asList().toSet())

fun Universe.next(): Universe =
        withRequiredDeadCells
                .let { universe -> universe.map { cell -> cell.nextState(universe) } }
                .filter { it is Alive }
                .toSet()
                .let(::Universe)

fun Universe.toggleCell(cell: Cell): Universe = Universe(cells.filterNot { it == cell }.toSet().plus(cell.toggle()))

fun Universe.createFrame(upperOutermost: Coordinate, origin: Coordinate = Coordinate(x = 0, y = 0)): Frame =
        filter { it isBetween origin and upperOutermost }
                .toSet()
                .let { rows -> Frame(cells = rows, origin = origin, upperOutermost = upperOutermost, universe = this) }

val Universe.withRequiredDeadCells: Universe
    get() = fold(cells) { universe, cell -> universe + cell.missingNeighbors(universe = this) }.let(::Universe)