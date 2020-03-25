package com.randomgametpnv.counters.entities

data class CounterDataUi(val typeOfEnergy: TypeOfEnergy, val date: List<MeasurementDataUi>)
data class MeasurementDataUi(val value: Int, val date: String, val unitOfMeasurement: String?)