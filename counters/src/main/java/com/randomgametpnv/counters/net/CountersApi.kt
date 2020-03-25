package com.randomgametpnv.counters.net

import com.randomgametpnv.counters.entities.CounterData
import retrofit2.http.*

interface CountersApi {

    @GET("client/my-counter/{energy}")
    suspend fun callColdWaterCounter(@Header("Authorization") header: String, @Path("energy") energy: String): List<CounterData>
}