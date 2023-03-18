package com.tutu.wrath.util


fun displayError(exception: Throwable) {
    Toaster.danger(exception.message ?: "Ops, algo de errado não esta certo! Tente novamente mais tarde.")
    console.log(exception)
}

fun <T> Result<T>.unwrap(onFailure: T): T {
    if (isSuccess) {
        return getOrDefault(onFailure)
    }

    displayError(exceptionOrNull() ?: Exception("Unknown Error"))

    return onFailure
}