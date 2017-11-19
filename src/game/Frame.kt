package game

data class Frame(
        val rows: Set<Cell>,
        val origin: Coordinate = Coordinate(x = 0, y = 0),
        val upperOutermost: Coordinate
) : Set<Cell> by rows