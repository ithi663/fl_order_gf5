package com.randomgametpnv.help.entities


import com.google.gson.annotations.SerializedName

data class Vote(@SerializedName("subject")
                val subject: String = "",
                @SerializedName("stamp")
                val stamp: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("body")
                val body: String = "",
                @SerializedName("vote_variant")
                val voteVariant: List<VoteVariantItem>?)


data class VoteVariantItem(@SerializedName("variant")
                           val variant: String = "",
                           @SerializedName("id")
                           val id: Int = 0)


