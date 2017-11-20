package game

sealed class Result<fail, success>

data class Success<fail, success>(val value: success) : Result<fail, success>()

data class Failure<fail, success>(val value: fail) : Result<fail, success>()

fun <failure, success> success.asSuccess(): Result<failure, success> = Success(value = this)

fun <failure, success> failure.asFailure(): Result<failure, success> = Failure(value = this)

fun <failure, success, newSuccess> Result<failure, success>.mapSuccess(
        transform: (success) -> newSuccess
): Result<failure, newSuccess> = when (this) {
    is Success -> transform(value).asSuccess()
    is Failure -> value.asFailure()
}

fun <failure, newFailure, success> Result<failure, success>.mapError(
        transform: (failure) -> newFailure
): Result<newFailure, success> = when (this) {
    is Success -> value.asSuccess()
    is Failure -> transform(value).asFailure()
}

fun <failure, success, newSuccess> Result<failure, success>.flatMapSuccess(
        transform: (success) -> Result<failure, newSuccess>
): Result<failure, newSuccess> = when (this) {
    is Success -> transform(value)
    is Failure -> value.asFailure()
}

fun <failure, newFailure, success> Result<failure, success>.flatMapError(
        transform: (failure) -> Result<newFailure, success>
): Result<newFailure, success> = when (this) {
    is Success -> value.asSuccess()
    is Failure -> transform(value)
}

fun <failure, success> Result<failure, success>.ifSuccess(fn: (success) -> Unit) {
    if (this is Success) fn(value)
}

fun <failure, success> Result<failure, success>.ifFailure(fn: (failure) -> Unit) {
    if (this is Failure) fn(value)
}