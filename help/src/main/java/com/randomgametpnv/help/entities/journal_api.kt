package com.randomgametpnv.help.entities


import com.google.gson.annotations.SerializedName

data class Journal(@SerializedName("delivery")
                   val delivery: String = "",
                   @SerializedName("flat_id")
                   val flatId: Int = 0,
                   @SerializedName("phone")
                   val phone: String = "",
                   @SerializedName("stamp")
                   val stamp: String = "",
                   @SerializedName("pickup")
                   val pickup: String = "",
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("message")
                   val message: String = "",
                   @SerializedName("person_id")
                   val personId: Int = 0)


