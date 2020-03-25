package com.randomgametpnv.counters.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.ApiData
import com.randomgametpnv.counters.entities.TypeOfEnergy
import kotlinx.coroutines.flow.Flow

interface CountersNet {

    suspend fun getColdCater(header: String, typeOfEnergy: TypeOfEnergy): Flow<ApiCall<ApiData>>
}