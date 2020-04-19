package com.randomgametpnv.sip.util

import androidx.lifecycle.Observer
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.sip_manager.SipManager
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import net.sourceforge.peers.Config
import java.util.concurrent.TimeUnit

class RegisterHandler(private val networkStateListener: NetworkStateListener, private val sipManager: SipManager) {


    private val regCount = 20
    private val registerTime = TimeUnit.SECONDS.toMillis(10)

    suspend fun handleRegistration(config: Config) {

        for (i: Int in 0..regCount) {
            val res = tryRegister(config)
            if (res) return@handleRegistration
            delay(registerTime)
        }
    }


    private suspend fun tryRegister(config: Config): Boolean {

        val res = CompletableDeferred<Boolean>()
        networkStateListener.waitActiveNetworkState()
        val state = sipManager.register(config)
        withContext(Dispatchers.Main) {
            state.observeForever(object : Observer<SipRegistrationState> {
                override fun onChanged(t: SipRegistrationState?) {
                    when (t) {
                        is SipRegistrationState.RegisteringSuccess -> {
                            res.complete(true)
                            state.removeObserver(this)
                        }
                        is SipRegistrationState.RegisteringError -> {
                            res.complete(false)
                            state.removeObserver(this)
                        }
                    }
                }
            })
        }
        return res.await()
    }
}