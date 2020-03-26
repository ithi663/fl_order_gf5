package com.randomgametpnv.cameras.net

import com.randomgametpnv.base.toApiResponseError
import com.randomgametpnv.cameras.entities.toUiData
import com.randomgametpnv.common_value_objects.ApiCall
import kotlinx.coroutines.flow.flow

class NetImpl(private val api: CameraApi): CameraNet {

    override fun getAllCameras(header: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = api.callCameras(header).map { it.toUiData() }
            emit(ApiCall.Success(data))
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }
}