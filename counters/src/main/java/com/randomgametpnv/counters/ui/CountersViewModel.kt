package com.randomgametpnv.counters.ui

import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.CounterDataUi
import com.randomgametpnv.counters.entities.TypeOfEnergy
import com.randomgametpnv.counters.entities.toApiCallUiData
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.counters.net.CountersNetImpl
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import org.koin.ext.scope

class CountersViewModel(database: AppDatabase, private val countersNet: CountersNet): ViewModel() {


    private val requestHeader = database.userDao().getUser().createRequestHeader()

    fun getCounterData(typeOfEnergy: TypeOfEnergy) = liveData {

        requestHeader?: return@liveData
        countersNet
            .getColdCater(requestHeader, typeOfEnergy)
            .map {
                it.toApiCallUiData(typeOfEnergy)
            }
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }
}