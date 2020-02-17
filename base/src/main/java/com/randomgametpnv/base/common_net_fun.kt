package com.randomgametpnv.base

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.UserData
import java.net.ConnectException

fun Throwable.toApiResponseError(): ApiCall<Nothing> {

    return when (this) {
        is ConnectException -> {
            ApiCall.ConnectException
        }
        else -> {
            ApiCall.ResponseError(0, this.message)
        }
    }
}

fun UserData?.createRequestHeader(): String {

    this?: throw Throwable("User data is empty")
    return "${this.tokenType} ${this.accessToken}"
}