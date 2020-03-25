package com.randomgametpnv.login.net

import com.randomgametpnv.login.entities.LoginEntities
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("login/access-token")
    suspend fun call(@Field ("username") userName: String, @Field("password") password: String, @Field("scope") scope: String = "person"): LoginEntities
}