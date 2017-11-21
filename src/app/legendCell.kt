package app

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.jsStyle
import react.dom.key

interface LegendCellProps : RProps {
    var coordinate: Long?
    var axis: String
}

val cellDimensions: Map<String, String> = mapOf(
        "width" to "20px",
        "height" to "20px"
)

class LegendCell(props: LegendCellProps): RComponent<LegendCellProps, RState>() {

    override fun RBuilder.render() {

        div {
            attrs {
                key = "$props.axis-legend-$props.coordinate"
                jsStyle = cellDimensions.plus("margin" to "2px").plus(centeredElementStyle).toJs()
            }

            props.coordinate?.let { +it.toString() }
        }
    }
}

fun RBuilder.legendCell(coordinate: Long? = null, axis: String) {

    child(LegendCell::class) {
        attrs.coordinate = coordinate
        attrs.axis = axis
    }
}