package com.randomgametpnv.login.net

import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.login.entities.LoginEntities
import kotlinx.coroutines.flow.Flow

interface Net {

    suspend fun login(userName: String, pass: String): Flow<ApiCall<LoginEntities>>
}