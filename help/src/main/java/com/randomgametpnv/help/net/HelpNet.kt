package com.randomgametpnv.help.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.entities.*
import kotlinx.coroutines.flow.Flow

interface HelpNet {

    suspend fun billsApiCall(header: String): Flow<ApiCall<BillsUiData>>
    suspend fun journalApiCall(header: String): Flow<ApiCall<List<JournalUiData>>>
    suspend fun alarmsApiCall(header: String): Flow<ApiCall<List<AlarmUiData>>>
    suspend fun votesApiCall(header: String): Flow<ApiCall<List<Vote>>>
    suspend fun voteApiCall(header: String, voteId: Int): Flow<ApiCall<Vote>>
    suspend fun submitVoteCall(header: String, voteId: Int, variantId: Int): Flow<ApiCall<VoteRespApi>>
}