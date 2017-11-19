package index

import app.*
import react.dom.*
import test.runTests
import kotlin.browser.*

fun main(args: Array<String>) {

    render(document.getElementById("root")) {

        if (window.location.pathname == "/test") {
            runTests()
        } else {
            app()
        }
    }
}
