package app

import kotlinext.js.js
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.button
import react.dom.div
import react.dom.jsStyle
import react.dom.key

data class Controls(
        val next: (Event) -> Unit,
        val toggle: (Event) -> Unit,
        val reset: (Event) -> Unit
)

fun RBuilder.controls(running: Boolean, multiverseControl: Controls) {

    div {
        attrs {
            key = "button-bar"
            jsStyle = centeredElementStyle.toJs()
        }

        buttonHelper(text = "Next", action = multiverseControl.next )

        if (running) "Stop" else { "Start" }.let { buttonHelper(text = it, action = multiverseControl.toggle) }

        buttonHelper(text = "Reset", action = multiverseControl.reset )
    }
}

fun RBuilder.buttonHelper(text: String, action: (Event) -> Unit) {
    button {
        +text
        attrs {
            key = "$text-button"
            jsStyle = js {
                borderStyle = "1px solid black"
                width = "60px"
                height = "20px"
                margin = "1px"
            }
            onClickFunction = action
        }
    }
}