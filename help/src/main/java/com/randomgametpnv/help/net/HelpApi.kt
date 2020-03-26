package com.randomgametpnv.help.net

import com.randomgametpnv.help.entities.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HelpApi {

    @GET("client/my-invoices")
    suspend fun callBills(@Header("Authorization") header: String): List<Bills>

    @GET("client/my-notifications")
    suspend fun callJournal(@Header("Authorization") header: String): List<Journal>

    @GET("client/my-alarms")
    suspend fun callAlarms(@Header("Authorization") header: String): List<Alarms>

    @GET("client/votes")
    suspend fun callVotes(@Header("Authorization") header: String): List<Vote>

    @GET("client/votes/{vote_id}")
    suspend fun callVote(@Header("Authorization") header: String, @Path("vote_id")voteId: Int): Vote

    @GET("/api/v1/client/votes/{vote_id}/submit-variant")
    suspend fun submitVariant(
        @Header("Authorization") header: String,
        @Path("vote_id")voteId: Int,
        @Query("vote_id")vote_Id: Int,
        @Query("variant_id")variant_Id: Int
    ): VoteRespApi
}