package com.randomgametpnv.counters.entities

import java.util.*

data class CounterDataUi(val typeOfEnergy: TypeOfEnergy, val date: List<MeasurementDataUi>)
data class MeasurementDataUi(val value: Double, val date: Date?, val unitOfMeasurement: String?)