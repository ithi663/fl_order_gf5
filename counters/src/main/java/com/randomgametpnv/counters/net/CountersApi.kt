package com.randomgametpnv.counters.net

import com.randomgametpnv.counters.entities.ApiData
import retrofit2.http.*

interface CountersApi {

    @GET("client/my-counter/coldwater")
    suspend fun callColdWaterCounter(@Header("Authorization") header: String): ApiData
}