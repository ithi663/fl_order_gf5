package com.randomgametpnv.counters.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.CounterData
import com.randomgametpnv.counters.entities.TypeOfEnergy
import kotlinx.coroutines.flow.Flow

interface CountersNet {

    suspend fun getColdCater(header: String, typeOfEnergy: TypeOfEnergy, start: String, end: String): Flow<ApiCall<List<CounterData>>>
}