package com.randomgametpnv.counters.ui

import android.util.Log
import androidx.lifecycle.*
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.counters.entities.TypeOfEnergy
import com.randomgametpnv.counters.entities.toApiCallUiData
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.database.AppDatabase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CountersViewModel(database: AppDatabase, private val countersNet: CountersNet) : ViewModel() {


    private val requestHeader = CompletableDeferred<String?>()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            requestHeader.complete(database.userDao().getUser().createRequestHeader())
        }
    }

    fun getCounterData(typeOfEnergy: TypeOfEnergy) = liveData {

        val d = Calendar.getInstance().time;
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS")
        val dateStart: String = sdf.format(d)

        val endL = d.time - TimeUnit.DAYS.toMillis(31);
        val end = Date(endL)
        val endStart: String = sdf.format(end)

        val header = requestHeader.await() ?: return@liveData
        countersNet
            .getColdCater(header, typeOfEnergy, start = dateStart, end = endStart)
            .map {
                it.toApiCallUiData(typeOfEnergy)
            }
            .flowOn(Dispatchers.IO)
            .collect {
                emit(it)
            }
    }
}