package com.randomgametpnv.help.ui

import android.util.Log
import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.help.entities.VoteRespApi
import com.randomgametpnv.help.net.HelpNet
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HelpViewModel(private val database: AppDatabase, private val helpNet: HelpNet) : ViewModel() {

    private val requestHeader = CompletableDeferred<String?>()
    private val _submitVoteRez = MutableLiveData<ApiCall<VoteRespApi>>()
    val submitVoteRez: LiveData<ApiCall<VoteRespApi>> = _submitVoteRez

    init {
        viewModelScope.launch(Dispatchers.IO) {
            requestHeader.complete(database.userDao().getUser().createRequestHeader())
        }
    }


    fun getBillsFromApi() = liveData {

        val header = requestHeader.await() ?: return@liveData
        helpNet
            .billsApiCall(header)
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    fun getJournalFromApi() = liveData {

        val header = requestHeader.await() ?: return@liveData
        helpNet
            .journalApiCall(header)
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    fun getAlarms() = liveData {

        val header = requestHeader.await() ?: return@liveData
        helpNet
            .alarmsApiCall(header)
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    fun getAllVotes() = liveData {

        val header = requestHeader.await() ?: return@liveData
        helpNet.votesApiCall(header)
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }

    fun submitVotes(voteId: Int, variantId: Int) = viewModelScope.launch {
        val header = requestHeader.await() ?: return@launch
        helpNet.submitVoteCall(header, voteId, variantId)
            .flowOn(Dispatchers.IO)
            .collect {
                _submitVoteRez.value = it
            }
    }
}