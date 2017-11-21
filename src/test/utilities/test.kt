package test.utilities

import kotlinx.html.style
import kotlinext.js.js
import react.RBuilder
import react.dom.div
import react.dom.jsStyle

fun RBuilder.test(description: String, fn: () -> Boolean): Unit {

    div {
        +description
        attrs.jsStyle = js {
            color = fn().let {
                when (this) {
                    true -> attrs.style = "green"
                    false -> attrs.style = "red"
                }
            }
        }
    }
}