package com.randomgametpnv.counters.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.counters.entities.ColdWater

class CountersNetImpl(private val countersApi: CountersApi): CountersNet {

    override suspend fun getColdCater(header: String, flat_id: Int): ApiCall<ColdWater> {

        return ApiCall.ResponseError(0, null)
    }
}