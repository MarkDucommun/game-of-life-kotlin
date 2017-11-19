package game

data class CellAndCoordinate(val cell: Cell, val inner: Coordinate)

infix fun CellAndCoordinate.and(outer: Coordinate) =
        cell.location.x >= inner.x
                && cell.location.x < inner.x + outer.x
                && cell.location.y >= inner.y
                && cell.location.y < inner.y + outer.y