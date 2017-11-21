package app

import game.*
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.jsStyle
import react.dom.key

fun RBuilder.visibleUniverse(frame: Frame, toggleCellFn: (game.Cell) -> Unit) {

    div {

        (0L until frame.upperOutermost.y).reversed().forEach { y ->

            div {
                attrs {
                    key = "y-$y"
                    jsStyle = centeredElementStyle.toJs()
                }

                legendCell(coordinate = y, axis = "y")

                row(
                        content = frame.filter { it.location.y == y }.toSet(),
                        length = frame.upperOutermost.x,
                        rowValue = y,
                        toggleCellFn = toggleCellFn
                )
            }
        }

        xLegend(maximumX = frame.upperOutermost.x)
    }
}