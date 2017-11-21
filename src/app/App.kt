package app

import game.*
import react.*
import kotlin.browser.window

interface MultiverseState : RState {
    var frame: Frame
    var running: Boolean
}

class App : RComponent<RProps, MultiverseState>() {

    override fun MultiverseState.init() {
        frame = Universe().createFrame(origin = Coordinate(x = 0, y = 0), upperOutermost = Coordinate(x = 50, y = 30))
        running = false
    }

    override fun RBuilder.render() {

        visibleUniverse(frame = state.frame, toggleCellFn = { cell ->

            if (!state.running) {
                setState {
                    this.frame = frame.toggleCell(cell)
                }
            }
        })

        controls(running = state.running, multiverseControl = Controls(
                next = {
                    setState {
                        frame = frame.next()
                    }
                },
                toggle = {
                    setState {
                        running = !running
                    }
                },
                reset = {
                    setState {
                        frame = frame.reset()
                    }
                }
        ))

        if (state.running) {

            window.setTimeout(timeout = 100, handler = {
                setState {
                    this.frame = frame.next()
                }
            })
        }
    }
}

fun RBuilder.app(handler: RHandler<RProps> = {}) = child(App::class, handler = handler)