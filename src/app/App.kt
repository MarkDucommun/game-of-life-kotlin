package app

import kotlinx.html.style
import react.*
import react.dom.div
import kotlinext.js.js
import react.dom.key
import game.*
import kotlinx.html.js.onClickFunction

interface MultiverseState : RState {
    var universe: Universe
    var frame: Frame
}

class App : RComponent<RProps, MultiverseState>() {

    override fun MultiverseState.init() {

        universe = Universe(setOf(
                alive(x = 0, y = 0)
        ))

        val frameResult = universe.createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = Coordinate(x = 10, y = 10))

        when (frameResult) {
            is Success -> frame = frameResult.value
            is Failure -> TODO()
        }
    }

    val centeredElementFlexStyle = js {
        display = "flex"
        alignItems = "center"
        justifyContent = "center"
    }

    val legendStyle = js {
        display = "flex"
        alignItems = "center"
        justifyContent = "center"
        width = 20
        height = 20
        margin = "2px"
    }

    override fun RBuilder.render() {

        (0L..9).forEach { invertedX ->

            val x = 9 - invertedX

            div {
                attrs.key = "x-$x"
                attrs.style = centeredElementFlexStyle
                div {
                    +x.toString()
                    attrs.key = "y-legend-$x"
                    attrs.style = legendStyle
                }
                (0L..9).forEach { y ->

                    val coordinate = Coordinate(x = x, y = y)

                    val cell = state.frame.find { it.location == coordinate } ?: coordinate.asDeadCell()

                    div {
                        attrs.key = "x-$x,y-$y"
                        attrs.style = js {
                            width = 20
                            height = 20
                            border = "1px solid black"
                            margin = "1px"
                            if (cell is Alive) backgroundColor = "red"
                        }
                        attrs.onClickFunction = { event ->
                            state.universe.plus(cell)
                        }
                    }
                }
            }
        }

        div {
            attrs.key = "x-legend"
            attrs.style = centeredElementFlexStyle

            div {
                attrs.style = js {
                    width = 20
                    height = 20
                    margin = "2px"
                }
            }
            (0..9).forEach { y ->
                div {
                    +y.toString()
                    attrs.key = "x-legend-$y"
                    attrs.style = legendStyle
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}