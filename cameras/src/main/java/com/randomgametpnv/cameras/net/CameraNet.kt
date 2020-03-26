package com.randomgametpnv.cameras.net

import com.randomgametpnv.cameras.entities.CameraDataUi
import com.randomgametpnv.common_value_objects.ApiCall
import kotlinx.coroutines.flow.Flow

interface CameraNet {

    fun getAllCameras(header: String): Flow<ApiCall<List<CameraDataUi>>>
}