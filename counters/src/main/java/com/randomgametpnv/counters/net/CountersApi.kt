package com.randomgametpnv.counters.net

import com.randomgametpnv.counters.entities.ColdWater
import retrofit2.http.*

interface CountersApi {

    @GET("flat/{flat_id}/counter/coldwater")
    suspend fun callColdWaterCounter(@Header("Authorization") header: String, @Path("flat_id") flatId: Int): ColdWater
}