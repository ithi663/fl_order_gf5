package com.randomgametpnv.help.net

import com.randomgametpnv.base.toApiResponseError
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.entities.toUiData
import kotlinx.coroutines.flow.flow

class HelpNetImpl(private val api: HelpApi) : HelpNet {

    override suspend fun makeBillsApiCall(header: String, flat_id: Int) = flow {

        try {
            val data = ApiCall.Success(api.callBills(header, flat_id).toUiData())
            emit(data)
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun makeCallJournalApiCall(header: String, flat_id: Int) = flow {

        try {
            val data = api.callJournal(header, flat_id).map { it.toUiData() }
            emit(ApiCall.Success(data))
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun makeVoteApiCall(
        header: String,
        skip: Int,
        limit: Int
    ) = flow {
        try {
            val data = api.callVotes(header, skip, limit).map { /*it.toUiData()*/ }
            emit(ApiCall.ResponseError(0, null))
        } catch (e: Throwable) {
            //emit(e.toApiResponseError())
        }
    }
}