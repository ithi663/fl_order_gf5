package com.randomgametpnv.help.entities


import com.google.gson.annotations.SerializedName

data class InvoicesItem(@SerializedName("date")
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


data class PaymentItem(@SerializedName("date")
                       val date: String = "",
                       @SerializedName("period")
                       val period: String = "",
                       @SerializedName("amount")
                       val amount: Int = 0)


data class Bills(@SerializedName("sip_login")
                 val sipLogin: String = "",
                 @SerializedName("invoices")
                 val invoices: List<InvoicesItem>?,
                 @SerializedName("stored")
                 val stored: String = "",
                 @SerializedName("ip")
                 val ip: String = "",
                 @SerializedName("name")
                 val name: String = "",
                 @SerializedName("disabled")
                 val disabled: String = "",
                 @SerializedName("id")
                 val id: Int = 0,
                 @SerializedName("mac")
                 val mac: String = "",
                 @SerializedName("key")
                 val key: String = "")


