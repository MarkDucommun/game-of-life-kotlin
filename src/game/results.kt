package game


sealed class Result<fail, success>

data class Success<fail, success>(val value: success) : Result<fail, success>()

data class Failure<fail, success>(val value: fail) : Result<fail, success>()

fun <failure, success, newSuccess> Result<failure, success>.map(
        transform: (success) -> newSuccess
): Result<failure, newSuccess> = when (this) {
    is Success -> Success(transform(value))
    is Failure -> Failure(value)
}

fun <failure, success, newSuccess> Result<failure, success>.flatMap(
        transform: (success) -> Result<failure, newSuccess>
): Result<failure, newSuccess> = when (this) {
    is Success -> transform(value)
    is Failure -> Failure(value)
}

fun <failure, success> Result<failure, success>.onSuccess(fn: (success) -> Unit) {
    if (this is Success) fn(value)
}

fun <failure, success> Result<failure, success>.onFailure(fn: (failure) -> Unit) {
    if (this is Failure) fn(value)
}