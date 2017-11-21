package test

import app.App
import game.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import test.utilities.grouping
import test.utilities.test

class TestRunner : RComponent<RProps, RState>() {

    override fun RBuilder.render() {

        grouping(description = "universe") {
            test(description = "next - one cell by itself dies") {
                universe(alive(x = 0, y = 0)).next() == universe()
            }

            test(description = "next - one dead cell surrounded by three live cells revitalizes") {
                universe(
                        alive(x = 1, y = -1),
                        alive(x = 0, y = 1),
                        alive(x = -1, y = -1)
                ).next() == universe(
                        alive(x = 0, y = 0)
                )
            }

            test(description = "next - a box remains stable") {
                universe(
                        alive(x = 0, y = 0),
                        alive(x = 0, y = 1),
                        alive(x = 1, y = 0),
                        alive(x = 1, y = 1)
                ).next() == universe(
                        alive(x = 0, y = 0),
                        alive(x = 0, y = 1),
                        alive(x = 1, y = 0),
                        alive(x = 1, y = 1)
                )
            }

            test(description = "next - a diagonal line flips consistently") {
                universe(
                        alive(x = 0, y = -1),
                        alive(x = 0, y = 0),
                        alive(x = 0, y = 1)
                ).next() == universe(
                        alive(x = -1, y = 0),
                        alive(x = 0, y = 0),
                        alive(x = 1, y = 0)
                )
            }
        }

        grouping(description = "frame") {
            test(description = "create - it can capture a portion of a universe") {

                val set = setOf(
                        alive(x = 0, y = 0),
                        alive(x = 1, y = 0),
                        dead(x = 0, y = 1)
                )

                Universe(set).createFrame(Coordinate(x = 2, y = 2)) == Frame(
                        cells = set,
                        upperOutermost = Coordinate(x = 2, y = 2),
                        universe = Universe(set)
                )
            }

            test(description = "create - it eliminates things not in the requested portion of a universe") {

                val set = setOf(
                        alive(x = 0, y = 0),
                        alive(x = 0, y = 2),
                        alive(x = 1, y = 1),
                        alive(x = 2, y = 0),
                        alive(x = 2, y = 2)
                )

                Universe(set).createFrame(Coordinate(x = 2, y = 2)) == Frame(
                        cells = setOf(
                                alive(x = 0, y = 0),
                                alive(x = 1, y = 1)
                        ),
                        upperOutermost = Coordinate(x = 2, y = 2),
                        universe = Universe(set)
                )
            }
        }

        grouping(description = "App") {
            test(description = "test") {

                val app = App()

                app.render()

                true
            }
        }
    }
}

fun RBuilder.runTests() = child(TestRunner::class) {}