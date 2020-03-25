package com.randomgametpnv.counters.ui

import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.CounterDataUi
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.counters.net.CountersNetImpl
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData
import kotlinx.coroutines.launch
import org.koin.ext.scope

class CountersViewModel(database: AppDatabase, private val countersNet: CountersNet): ViewModel() {


    private val requestHeader = database.userDao().getUser().createRequestHeader()

    fun getCounterData() = liveData<CounterDataUi> {

        requestHeader?: return@liveData
        countersNet.getColdCater(requestHeader)
    }
}