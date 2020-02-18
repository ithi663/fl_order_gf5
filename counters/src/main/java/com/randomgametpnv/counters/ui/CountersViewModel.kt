package com.randomgametpnv.counters.ui

import androidx.lifecycle.*
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.CounterDataUi
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.counters.net.CountersNetImpl
import com.randomgametpnv.database.AppDatabase
import kotlinx.coroutines.launch
import org.koin.ext.scope

class CountersViewModel(private val database: AppDatabase, private val countersNet: CountersNet): ViewModel() {


    private val _counterRes = MutableLiveData<ApiCall<CounterDataUi>>()
    val counterRes: LiveData<ApiCall<CounterDataUi>> = _counterRes

    fun getCounterData() {

    }
}