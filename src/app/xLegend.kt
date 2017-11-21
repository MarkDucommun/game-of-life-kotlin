package app

import react.RBuilder
import react.dom.div
import react.dom.jsStyle
import react.dom.key

fun RBuilder.xLegend(maximumX: Long) {

    div {
        attrs {
            key = "x-legend"
            jsStyle = centeredElementStyle.toJs()
        }

        legendCell(axis = "origin")

        (0 until maximumX).forEach { x ->
            legendCell(coordinate = x, axis = "x")
        }
    }
}