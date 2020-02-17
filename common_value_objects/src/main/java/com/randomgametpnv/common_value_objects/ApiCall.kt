package com.randomgametpnv.common_value_objects

sealed class ApiCall<out T> {

    object Loading: ApiCall<Nothing>()
    data class Success<out T>(val data: T): ApiCall<T>()
    data class ResponseError(val id: Int?, val message: String?): ApiCall<Nothing>()
    object ConnectException: ApiCall<Nothing>()
}