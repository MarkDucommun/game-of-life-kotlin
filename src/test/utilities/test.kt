package test.utilities

import game.Failure
import game.Result
import game.Success
import kotlinx.html.style
import kotlinext.js.js
import react.RBuilder
import react.dom.div

fun RBuilder.test(description: String, fn: () -> Result<String, Unit>): Unit {

    div {
        +description
        fn().apply {
            when (this) {
                is Success -> attrs.style = js { color = "green" }
                is Failure -> {
                    +" - FAILS - $value"
                    attrs.style = js { color = "red" }
                }
            }
        }
    }
}

fun RBuilder.simpleTest(description: String, fn: () -> Boolean): Unit {

    div {
        +description
        fn().apply {
            when (this) {
                true -> attrs.style = js { color = "green" }
                false -> {
                    +" - FAILS"
                    attrs.style = js { color = "red" }
                }
            }
        }
    }
}