package test

import game.*
import react.*
import test.utilities.grouping
import test.utilities.succeedsAndShouldReturn
import test.utilities.test

interface AppState {

}

class TestRunner : RComponent<RProps, RState>() {

    override fun RBuilder.render() {

        grouping(description = "cell") {
            test(description = "toggle - kills an alive cell") {
                alive(x = 0, y = 0)
            }

            test(description = "toggle - revives a dead cell") {

            }
        }

        grouping(description = "universe") {

            test(description = "next - one cell by itself dies") {
                universe(alive(x = 0, y = 0)).next() succeedsAndShouldReturn universe()
            }

            test(description = "next - one dead cell surrounded by three live cells revitalizes") {
                universe(
                        alive(x = 1, y = -1),
                        alive(x = 0, y = 1),
                        alive(x = -1, y = -1)
                ).next() succeedsAndShouldReturn universe(
                        alive(x = 0, y = 0)
                )
            }

            test(description = "next - a box remains stable") {
                universe(
                        alive(x = 0, y = 0),
                        alive(x = 0, y = 1),
                        alive(x = 1, y = 0),
                        alive(x = 1, y = 1)
                ).next() succeedsAndShouldReturn universe(
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
                ).next() succeedsAndShouldReturn universe(
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

                Universe(set).createFrame(Coordinate(x = 2, y = 2)) succeedsAndShouldReturn Frame(
                        rows = set,
                        upperOutermost = Coordinate(x = 2, y = 2)
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

                Universe(set).createFrame(Coordinate(x = 2, y = 2)) succeedsAndShouldReturn Frame(
                        rows = setOf(
                                alive(x = 0, y = 0),
                                alive(x = 1, y = 1)
                        ),
                        upperOutermost = Coordinate(x = 2, y = 2)
                )
            }
        }
    }
}

fun RBuilder.runTests() = child(TestRunner::class) {}