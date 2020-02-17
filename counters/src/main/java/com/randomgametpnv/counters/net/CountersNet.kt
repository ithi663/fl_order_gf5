package com.randomgametpnv.counters.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.ColdWater

interface CountersNet {

    suspend fun getColdCater(header: String, flat_id: Int): ApiCall<ColdWater>
}