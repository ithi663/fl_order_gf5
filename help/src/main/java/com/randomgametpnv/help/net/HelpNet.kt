package com.randomgametpnv.help.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.entities.BillsUiData
import com.randomgametpnv.help.entities.JournalUiData
import kotlinx.coroutines.flow.Flow

interface HelpNet {

    suspend fun makeBillsApiCall(header: String, flat_id: Int): Flow<ApiCall<BillsUiData>>
    suspend fun makeCallJournalApiCall(header: String, flat_id: Int): Flow<ApiCall<List<JournalUiData>>>
    suspend fun makeVoteApiCall(header: String, skip: Int, limit: Int): Flow<ApiCall<List<JournalUiData>>>
}