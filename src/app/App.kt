package app

import kotlinx.html.style
import react.*
import react.dom.div
import kotlinext.js.js
import react.dom.key
import game.*
import kotlinx.html.js.onClickFunction
import react.dom.button

interface MultiverseState : RState {
    var universe: Universe
    var frame: Frame
    var outermost: Coordinate
}

class App : RComponent<RProps, MultiverseState>() {

    override fun MultiverseState.init() {
        outermost = Coordinate(x = 50, y = 30)
        universe = Universe()
        universe
                .createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = outermost)
                .ifSuccess { frame = it }
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

        (0L..state.outermost.y).forEach { invertedY ->

            val y = state.outermost.y - invertedY

            div {
                attrs.key = "y-$y"
                attrs.style = centeredElementFlexStyle
                div {
                    +y.toString()
                    attrs.key = "y-legend-$y"
                    attrs.style = legendStyle
                }
                (0L..state.outermost.x).forEach { x ->

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

                            val lessCells: Set<Cell> = state.universe.cells.filterNot { it == cell }.toSet()

                            val universe = Universe(lessCells.plus(cell.toggle()))

                            universe
                                    .createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = state.outermost)
                                    .ifSuccess {
                                        setState {
                                            this.universe = universe
                                            this.frame = it
                                        }
                                    }
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
            (0..state.outermost.x).forEach { y ->
                div {
                    +y.toString()
                    attrs.key = "x-legend-$y"
                    attrs.style = legendStyle
                }
            }
        }

        div {
            attrs.key = "button-bar"
            attrs.style = centeredElementFlexStyle

            button {
                +"Next"
                attrs.style = js {
                    width = 60
                    height = 20
                    border = "1px solid black"
                    margin = "1px"
                }
                attrs.onClickFunction = { event ->

                    state.universe
                            .next()
                            .flatMapSuccess { universe ->
                                universe
                                        .createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = state.outermost)
                                        .mapSuccess { universe to it }
                            }
                            .ifSuccess { (universe, frame) ->
                                setState {
                                    this.universe = universe
                                    this.frame = frame
                                }
                            }
                }
            }

            button {
                +"Play"
                attrs.style = js {
                    width = 60
                    height = 20
                    border = "1px solid black"
                    margin = "1px"
                }
                attrs.onClickFunction = { event ->

                    state.universe
                            .next()
                            .flatMapSuccess {
                                setState {
                                    this.universe = it
                                }
                                it.createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = state.outermost)
                            }
                            .ifSuccess {
                                setState {
                                    this.frame = it
                                }
                            }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}