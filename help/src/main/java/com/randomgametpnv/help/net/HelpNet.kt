package com.randomgametpnv.help.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.entities.*
import kotlinx.coroutines.flow.Flow

interface HelpNet {

    suspend fun billsApiCall(header: String): Flow<ApiCall<List<Bills>>>
    suspend fun journalApiCall(header: String): Flow<ApiCall<List<Journal>>>
    suspend fun alarmsApiCall(header: String): Flow<ApiCall<List<Alarms>>>
    suspend fun votesApiCall(header: String): Flow<ApiCall<List<Vote>>>
    suspend fun voteApiCall(header: String, voteId: Int): Flow<Vote>
}