package app

import game.*
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.div
import react.dom.jsStyle
import react.dom.key

val cellDimensions = mapOf(
        "width" to "20px",
        "height" to "20px"
)

val cellStyle = cellDimensions.plus(borderStyleL).plus("margin" to "1px")

fun RBuilder.visibleUniverse(frame: Frame, toggleCellFn: (Cell) -> Unit) {

    div {

        (0L until frame.upperOutermost.y).reversed().forEach { y ->

            div {
                attrs {
                    key = "y-$y"
                    jsStyle = centeredElementStyle.toJs()
                }

                legendCell(coordinate = y, axis = "y")

                (0L until frame.upperOutermost.x).forEach { x ->

                    val coordinate = Coordinate(x = x, y = y)

                    val cell = frame.find { it.location == coordinate } ?: coordinate.asDeadCell()

                    div {
                        attrs {
                            key = "x-$x,y-$y"
                            jsStyle = cellStyle.let { if (cell is Alive) it.plus("backgroundColor" to "red") else it }.toJs()
                            onClickFunction = {
                                toggleCellFn(cell)
                            }
                        }
                    }
                }
            }
        }

        xLegend(maximumX = frame.upperOutermost.x)
    }
}