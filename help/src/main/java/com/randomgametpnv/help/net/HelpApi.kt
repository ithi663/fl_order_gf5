package com.randomgametpnv.help.net

import com.randomgametpnv.help.entities.Alarms
import com.randomgametpnv.help.entities.Bills
import com.randomgametpnv.help.entities.Journal
import com.randomgametpnv.help.entities.Vote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

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
}