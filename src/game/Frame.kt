package game

data class Frame(
        val cells: Set<Cell>,
        val origin: Coordinate = Coordinate(x = 0, y = 0),
        val upperOutermost: Coordinate,
        val universe: Universe
) : Set<Cell> by cells

fun Frame.next(): Frame = this updateWith universe.next()

fun Frame.toggleCell(cell: Cell): Frame = this updateWith universe.toggleCell(cell = cell)

fun Frame.reset(): Frame = this updateWith Universe()

infix fun Frame.updateWith(universe: Universe): Frame =
        universe.createFrame(origin = origin, upperOutermost = upperOutermost)