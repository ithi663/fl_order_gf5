package com.randomgametpnv.counters.entities


import com.google.gson.annotations.SerializedName

data class FlatDetectorItem(@SerializedName("measure")
                            val measure: Int = 0,
                            @SerializedName("stamp")
                            val stamp: String = "",
                            @SerializedName("id")
                            val id: Int = 0,
                            @SerializedName("value")
                            val value: Int = 0)


data class FlatMeterEntity(@SerializedName("tarif")
                           val tarif: Tarif,
                           @SerializedName("measure")
                           val measure: Int = 0,
                           @SerializedName("port")
                           val port: Int = 0,
                           @SerializedName("name")
                           val name: String = "",
                           @SerializedName("stamp")
                           val stamp: String = "",
                           @SerializedName("flat_detector")
                           val flatDetector: List<FlatDetectorItem>?,
                           @SerializedName("tarif_id")
                           val tarifId: Int = 0,
                           @SerializedName("correction")
                           val correction: String = "",
                           @SerializedName("meter_type")
                           val meterType: List<MeterTypeItem>?,
                           @SerializedName("value")
                           val value: Int = 0)


data class ColdWater(@SerializedName("tarif")
                     val tarif: Tarif,
                     @SerializedName("measure")
                     val measure: Int = 0,
                     @SerializedName("port")
                     val port: Int = 0,
                     @SerializedName("meter_data_sum")
                     val meterDataSum: Int = 0,
                     @SerializedName("name")
                     val name: String = "",
                     @SerializedName("stamp")
                     val stamp: String = "",
                     @SerializedName("flat_detector")
                     val flatDetector: List<FlatDetectorItem>?,
                     @SerializedName("FlatMeterEntity")
                     val flatMeterEntity: FlatMeterEntity,
                     @SerializedName("tarif_id")
                     val tarifId: Int = 0,
                     @SerializedName("correction")
                     val correction: String = "",
                     @SerializedName("meter_type")
                     val meterType: List<MeterTypeItem>?,
                     @SerializedName("value")
                     val value: Int = 0)


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


