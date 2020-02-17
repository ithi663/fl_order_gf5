package com.randomgametpnv.help.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private val _billsResult = MutableLiveData<ApiCall<BillsUiData>>()
    val billsResult: LiveData<ApiCall<BillsUiData>> = _billsResult

    private val _journalResult = MutableLiveData<ApiCall<List<JournalUiData>>>()
    val journalResult: LiveData<ApiCall<List<JournalUiData>>> = _journalResult

    fun getBillsFromApi() {

        viewModelScope.launch(Dispatchers.IO) {
            val userData = database.userDao().getUser()
            Log.d("QWERTYHH", "head  -> ${userData.createRequestHeader()}")
            helpNet
                .makeBillsApiCall(userData.createRequestHeader(), userData!!.id)
                .collect {
                    withContext(Dispatchers.Main) {
                        _billsResult.value = it
                    }
                }
        }
    }

    fun getJournalFromApi() {

        viewModelScope.launch(Dispatchers.IO) {
            val userData = database.userDao().getUser()
            helpNet
                .makeCallJournalApiCall(userData.createRequestHeader(), userData!!.id)
                .collect {
                    withContext(Dispatchers.Main) {
                        _journalResult.value = it
                    }
                }
        }
    }
}