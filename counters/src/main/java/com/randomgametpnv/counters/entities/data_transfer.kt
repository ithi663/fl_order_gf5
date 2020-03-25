package com.randomgametpnv.counters.entities

import com.randomgametpnv.common_value_objects.ApiCall


const val dayLimit = 30

fun ApiData.toUiData(energy: TypeOfEnergy): CounterDataUi {

    var count = 0
    val list = mutableListOf<MeasurementDataUi>()

    this.list.forEach {mList ->

        mList.meterData?.let {mDate ->
            mDate.forEach {
                if (count > dayLimit) return@toUiData CounterDataUi(energy, list)
                count +=1
                list.add(MeasurementDataUi(it.value, it.day, ""))
            }
        }
    }

    return CounterDataUi(energy, list)
}


fun ApiCall<ApiData>.toApiCallUiData(energy: TypeOfEnergy): ApiCall<CounterDataUi> {
    return when (this) {
        is ApiCall.Loading -> {ApiCall.Loading}
        is ApiCall.Success -> {ApiCall.Success(this.data.toUiData(energy))}
        is ApiCall.ResponseError -> {ApiCall.ResponseError(this.id, this.message)}
        is ApiCall.ConnectException -> {ApiCall.ConnectException}
    }
}