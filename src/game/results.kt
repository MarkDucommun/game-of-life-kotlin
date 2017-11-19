package game


sealed class Result<fail, success>

data class Success<fail, success>(val value: success) : Result<fail, success>()

data class Failure<fail, success>(val value: fail) : Result<fail, success>()