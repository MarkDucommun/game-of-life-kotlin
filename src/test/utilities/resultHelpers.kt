package test.utilities

import game.Failure
import game.Result
import game.Success


infix fun <fail, success> Result<fail, success>.succeedsAnd(fn: (success) -> Result<String, Unit>): Result<String, Unit> {
    return when (this) {
        is Success -> fn(this.value)
        is Failure -> Failure("Result was a failure, but should have been a success")
    }
}

infix fun <fail, success> Result<fail, success>.succeedsAndShouldReturn(expected: success): Result<String, Unit> {

    return this.succeedsAnd { content ->

        if (content == expected) {
            Success(Unit)
        } else {
            Failure("Actual does not match expected\n${content}")
        }
    }
}

infix fun <fail, success> Result<fail, success>.failsAnd(fn: (fail) -> Result<String, Unit>): Result<String, Unit> {

    return when (this) {
        is Success -> Failure("Result was a success, but should have been a failure")
        is Failure -> fn(this.value)
    }
}

infix fun <fail, success> Result<fail, success>.failsAndShouldReturn(expected: fail): Result<String, Unit> {

    return this.failsAnd { content ->
        if (content == expected) {
            Failure("Actual does not match expected")
        } else {
            Success(Unit)
        }
    }
}