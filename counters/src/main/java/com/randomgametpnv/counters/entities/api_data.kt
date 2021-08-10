package com.randomgametpnv.counters.entities


import com.google.gson.annotations.SerializedName

data class FlatDetectorItem(@SerializedName("measure")
                            val measure: Float = 0f,
                            @SerializedName("stamp")
                            val stamp: String = "",
                            @SerializedName("id")
                            val id: Int = 0,
                            @SerializedName("value")
                            val value: Float = 0f)


data class CounterData(@SerializedName("meter_type_id")
                       val meterTypeId: Int = 0,
                       @SerializedName("stamp")
                       val stamp: String = "",
                       @SerializedName("meter_type")
                       val meterType: List<MeterTypeItem>?,
                       @SerializedName("tarif")
                       val tarif: Tarif,
                       @SerializedName("measure")
                       val measure: Int = 0,
                       @SerializedName("meter_data")
                       val meterData: List<MeterDataItem>?,
                       @SerializedName("port")
                       val port: Int = 0,
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("flat_detector")
                       val flatDetector: List<FlatDetectorItem>?,
                       @SerializedName("id")
                       val id: Int = 0,
                       @SerializedName("tarif_id")
                       val tarifId: Int = 0,
                       @SerializedName("correction")
                       val correction: String = "",
                       @SerializedName("value")
                       val value: Float = 0f)


data class MeterDataItem(@SerializedName("day")
                         val day: String = "",
                         @SerializedName("value")
                         val value: Double = 0.0)


data class MeterTypeItem(@SerializedName("unit")
                         val unit: String = "",
                         @SerializedName("name")
                         val name: String = "",
                         @SerializedName("scale")
                         val scale: Int = 0,
                         @SerializedName("id")
                         val id: Int = 0)


data class Tarif(@SerializedName("price")
                 val price: String = "",
                 @SerializedName("name")
                 val name: String = "",
                 @SerializedName("id")
                 val id: Int = 0)


