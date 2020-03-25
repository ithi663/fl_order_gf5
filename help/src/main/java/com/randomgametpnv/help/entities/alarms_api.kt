package com.randomgametpnv.help.entities


import com.google.gson.annotations.SerializedName

data class Alarms(@SerializedName("flat_sensor")
                  val flatSensor: FlatSensor,
                  @SerializedName("stop")
                  val stop: String = "",
                  @SerializedName("stamp")
                  val stamp: String = "",
                  @SerializedName("id")
                  val id: Int = 0,
                  @SerializedName("last_check")
                  val lastCheck: String = "",
                  @SerializedName("open")
                  val open: String = "")


data class FlatSensor(@SerializedName("flat_id")
                      val flatId: Int = 0,
                      @SerializedName("acc_id")
                      val accId: Int = 0)


