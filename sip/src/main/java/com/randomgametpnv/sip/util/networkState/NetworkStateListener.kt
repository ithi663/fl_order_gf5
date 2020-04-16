package com.randomgametpnv.sip.util.networkState

import androidx.lifecycle.LiveData
import com.randomgametpnv.sip.entities.NetState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

interface NetworkStateListener {

    fun getStateListener(): LiveData<NetState>
    suspend fun waitActiveNetworkState(): Boolean
}