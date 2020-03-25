package com.randomgametpnv.cameras.net

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CameraApi {

    @GET("client/camera")
    suspend fun callCameras(@Header("Authorization") header: String): List<Int>

    @GET("client/camera/{camera_id}")
    suspend fun callCamera(@Header("Authorization") header: String, @Path("camera_id")camera_Id: String)
}