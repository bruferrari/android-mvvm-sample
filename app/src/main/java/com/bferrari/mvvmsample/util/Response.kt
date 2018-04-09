package com.bferrari.mvvmsample.util

class Response(val status: Status,
               val data: Any? = null,
               val error: Throwable? = null) {
    companion object {
        fun loading() = Response(Status.LOADING)

        fun success(data: Any) = Response(Status.SUCCESS, data)

        fun error(error: Throwable) = Response(Status.ERROR, error = error)
    }
}