package com.randomgametpnv.help.net

import com.randomgametpnv.help.entities.Bills
import com.randomgametpnv.help.entities.Journal
import com.randomgametpnv.help.entities.Vote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HelpApi {

    @GET("flat/{flat_id}/invoices")
    suspend fun callBills(@Header("Authorization") header: String, @Path("flat_id") flatId: Int): Bills

    @GET("flat/{flat_id}/notifications")
    suspend fun callJournal(@Header("Authorization") header: String, @Path("flat_id") flatId: Int): List<Journal>

    @GET("vote/{skip}/{limit}")
    suspend fun callVote(@Header("Authorization") header: String, @Path("skip") skip: Int, @Path("limit") limit: Int): List<Vote>
}