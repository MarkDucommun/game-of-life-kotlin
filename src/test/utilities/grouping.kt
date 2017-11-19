package test.utilities

import react.RBuilder
import react.dom.div
import react.dom.h4

fun RBuilder.grouping(description: String, fn: RBuilder.() -> Unit) {
    div {
        h4 { +description }
        fn()
    }
}