package com.randomgametpnv.login.net

import android.util.Log
import com.randomgametpnv.common_value_objects.ApiCall
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

class NetImpl(private val api: Api): Net {

    override suspend fun login(userName: String, pass: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = ApiCall.Success(api.call(userName, pass))
            emit(data)
        } catch (e: Throwable) {
            when (e) {
                is ConnectException -> {emit(ApiCall.ConnectException)}
                else -> {emit(ApiCall.ResponseError(0, e.message))}
            }
        }
    }
}