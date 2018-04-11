package com.bferrari.mvvmsample.util

class Response<T>(val status: Status,
               val data: T? = null,
               val error: Throwable? = null) {

    companion object {
        fun <T> loading() = Response<T>(Status.LOADING)

        fun <T> success(data: T) = Response(Status.SUCCESS, data)

        fun <T> error(error: Throwable) = Response<T>(Status.ERROR, error = error)
    }
}