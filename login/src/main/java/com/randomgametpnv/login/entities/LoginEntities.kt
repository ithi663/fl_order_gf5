package com.randomgametpnv.login.entities

import com.google.gson.annotations.SerializedName

data class LoginEntities(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("token_type")
    val tokenType: String = ""
)