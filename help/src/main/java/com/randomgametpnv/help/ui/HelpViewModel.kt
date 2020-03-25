package com.randomgametpnv.help.ui

import android.util.Log
import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.help.entities.Bills
import com.randomgametpnv.help.entities.BillsUiData
import com.randomgametpnv.help.entities.Journal
import com.randomgametpnv.help.entities.JournalUiData
import com.randomgametpnv.help.net.HelpNet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HelpViewModel(private val database: AppDatabase, private val helpNet: HelpNet) : ViewModel() {


    private val requestHeader = database.userDao().getUser().createRequestHeader()

    fun getBillsFromApi() = liveData(Dispatchers.IO) {

        requestHeader?: return@liveData
        val userData = database.userDao().getUser()
        helpNet
            .makeBillsApiCall(requestHeader, userData!!.id)
            .collect {
                withContext(Dispatchers.Main) {
                    emit(it)
                }
            }
    }

    fun getJournalFromApi() = liveData(Dispatchers.IO) {
        requestHeader?: return@liveData
        val userData = database.userDao().getUser()
        helpNet
            .makeCallJournalApiCall(requestHeader, userData!!.id)
            .collect {
                withContext(Dispatchers.Main) {
                    emit(it)
                }
            }
    }
}