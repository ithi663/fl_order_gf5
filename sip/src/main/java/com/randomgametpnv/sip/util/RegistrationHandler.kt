package com.randomgametpnv.sip.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.randomgametpnv.sip.entities.ServiceNotificationType
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.sip_manager.SipManager
import kotlinx.coroutines.*
import net.sourceforge.peers.Config
import org.koin.ext.scope
import java.util.concurrent.TimeUnit

class RegistrationHandler(private val sipManager: SipManager, private val scope: CoroutineScope, private val config: CompletableDeferred<Config>) {


    private val regCount = 20
    private val registerTime = TimeUnit.SECONDS.toMillis(10)
    val events = MutableLiveData<ServiceNotificationType>()
    private var registerJob: Job? = null


    fun handleRegistration() {

        registerJob?.cancel()

        registerJob = scope.launch {

            withContext(Dispatchers.Main) { events.value = ServiceNotificationType.Registering }
            for (i: Int in 0..regCount) {
                val res = tryRegister(config.await())
                if (res) {
                    withContext(Dispatchers.Main) {
                        events.value = ServiceNotificationType.StatusOk
                    }
                    return@launch
                }
                delay(registerTime)
            }

            withContext(Dispatchers.Main) { events.value = ServiceNotificationType.StatusError }
        }
    }


    private suspend fun tryRegister(config: Config): Boolean {

        val res = CompletableDeferred<Boolean>()
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