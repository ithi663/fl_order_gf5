package com.randomgametpnv.cameras.net

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CameraApi {

    @GET("flat/{flat_id}/notifications")
    suspend fun callJournal(@Header("Authorization") header: String, @Path("flat_id") flatId: Int): List<Int>
}