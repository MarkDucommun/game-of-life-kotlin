package app

import game.Frame
import game.Universe
import kotlinx.html.style
import react.*
import react.dom.div
import kotlin.browser.window

interface MultiverseState : RState {
    var universe: Universe
    var frame: Frame
}

class App : RComponent<RProps, MultiverseState>() {

    override fun RBuilder.render() {

        (1..100).forEach {

            div {
                attrs.style = kotlinext.js.js {
                    display = "flex"
                }

                (1..100).forEach {
                    div {
                        attrs.style = kotlinext.js.js {
                            flex = 1
                            width = 10
                            height = 10
                            border = "1px solid black"
                            margin = "1px"
                        }

                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}