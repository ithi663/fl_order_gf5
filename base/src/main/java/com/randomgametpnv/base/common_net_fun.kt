package com.randomgametpnv.base

import android.util.Log
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

fun UserData?.createRequestHeader(): String? {

    this?: return null
    return "${this.tokenType} ${this.accessToken}"
}