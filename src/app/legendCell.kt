package app

import react.RBuilder
import react.dom.div
import react.dom.jsStyle
import react.dom.key

fun RBuilder.legendCell(coordinate: Long? = null, axis: String) {

    div {
        attrs {
            key = "$axis-legend-$coordinate"
            jsStyle = cellDimensions.plus("margin" to "2px").plus(centeredElementStyle).toJs()
        }

        coordinate?.let { +it.toString() }
    }
}