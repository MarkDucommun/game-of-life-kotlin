package app

val borderStyleL = mapOf("border" to "1px solid black")

val centeredElementStyle = mapOf(
        "display" to "flex",
        "alignItems" to "center",
        "justifyContent" to "center"
)

fun Map<String, String>.toJs(): dynamic {

    val map = this

    return kotlinext.js.js {

        val jsObject = this

        map.forEach { (key, value) ->
            jsObject[key] = value
        }
    }
}