package com.randomgametpnv.cameras.entities


import com.google.gson.annotations.SerializedName

data class Camera(@SerializedName("button")
                  val button: String = "",
                  @SerializedName("descr")
                  val descr: String = "",
                  @SerializedName("ip")
                  val ip: String = "",
                  @SerializedName("name")
                  val name: String = "",
                  @SerializedName("id")
                  val id: Int = 0,
                  @SerializedName("mac")
                  val mac: String = "")


