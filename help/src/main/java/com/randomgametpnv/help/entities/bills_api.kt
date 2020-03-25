package com.randomgametpnv.help.entities


import com.google.gson.annotations.SerializedName

data class PaymentItem(@SerializedName("date")
                       val date: String = "",
                       @SerializedName("period")
                       val period: String = "",
                       @SerializedName("amount")
                       val amount: Int = 0)


data class Bills(@SerializedName("date")
                 val date: String = "",
                 @SerializedName("amount")
                 val amount: Int = 0,
                 @SerializedName("flat_id")
                 val flatId: Int = 0,
                 @SerializedName("stamp")
                 val stamp: String = "",
                 @SerializedName("payment")
                 val payment: List<PaymentItem>?,
                 @SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("debt")
                 val debt: Int = 0,
                 @SerializedName("to_pay")
                 val toPay: Int = 0)


