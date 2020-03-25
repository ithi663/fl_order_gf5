package com.randomgametpnv.help.net

import com.randomgametpnv.base.toApiResponseError
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.entities.JournalUiData
import com.randomgametpnv.help.entities.Vote
import com.randomgametpnv.help.entities.toUiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HelpNetImpl(private val api: HelpApi) : HelpNet {

    override suspend fun billsApiCall(header: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = ApiCall.Success(api.callBills(header).toUiData())
            emit(data)
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun journalApiCall(header: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = api.callJournal(header).map { it.toUiData() }
            emit(ApiCall.Success(data))
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun alarmsApiCall(header: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = api.callAlarms(header).map { it.toUiData() }
            emit(ApiCall.Success(data))
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun votesApiCall(header: String) = flow {

        emit(ApiCall.Loading)
        try {
            val data = api.callVotes(header)
            emit(ApiCall.Success(data))
        } catch (e: Throwable) {
            emit(e.toApiResponseError())
        }
    }

    override suspend fun voteApiCall(header: String, voteId: Int) = flow {

        //emit(ApiCall.Loading)
        try {
            val data = api.callVote(header, voteId)
            emit(data)
        } catch (e: Throwable) {
            //emit(e.toApiResponseError())
        }
    }
}