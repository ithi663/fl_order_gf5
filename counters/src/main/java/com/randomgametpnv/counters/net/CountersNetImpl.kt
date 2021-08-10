package com.randomgametpnv.counters.net

import android.util.Log
import com.randomgametpnv.base.toApiResponseError
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.CounterData
import com.randomgametpnv.counters.entities.TypeOfEnergy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountersNetImpl(private val countersApi: CountersApi): CountersNet {

    override suspend fun getColdCater(header: String, typeOfEnergy: TypeOfEnergy, start:String, end: String): Flow<ApiCall<List<CounterData>>> = flow{
        emit(ApiCall.Loading)
        try {
            val response = countersApi.callColdWaterCounter(header, typeOfEnergy.name, start, end)
            emit(ApiCall.Success(response))
        } catch (e: Throwable) {
            Log.d("SLDKJFLS", "------------>${e.message}")
            emit(e.toApiResponseError())
        }
    }
}