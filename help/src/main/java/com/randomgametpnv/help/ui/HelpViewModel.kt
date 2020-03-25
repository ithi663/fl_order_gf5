package com.randomgametpnv.help.ui

import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.help.net.HelpNet
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HelpViewModel(private val database: AppDatabase, private val helpNet: HelpNet) : ViewModel() {

    private val requestHeader = CompletableDeferred<String?>()

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
}