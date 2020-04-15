package com.randomgametpnv.sip.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.randomgametpnv.sip.entities.SipServiceState
import kotlinx.coroutines.*
import net.sourceforge.peers.Config
import net.sourceforge.peers.FileLogger
import net.sourceforge.peers.Logger
import net.sourceforge.peers.sip.Utils
import net.sourceforge.peers.sip.core.useragent.SipListener
import net.sourceforge.peers.sip.core.useragent.UserAgent
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException
import net.sourceforge.peers.sip.transport.SipRequest
import net.sourceforge.peers.sip.transport.SipResponse

class SipManager(context: Context) : SipListener {

    private val _tag = "SIP_TAG"
    val state = MutableLiveData<SipServiceState>()


    private var userAgent: UserAgent? = null
    private var donDigidonService =
        DonDigidonService(context)
    private var javaxSoundManager: SipAudioManager =
        SipAudioManager()

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var sipRequest: SipRequest? = null


    fun createSipAgent(config: Config) {
        val logger: Logger = FileLogger(null)
        userAgent = UserAgent(this, config, logger, javaxSoundManager)
        scope.launch {
            try {
                userAgent?.register()
            } catch (e: SipUriSyntaxException) {
                Log.d(_tag, "register userAgent error -> $e")
            }
        }
    }

    fun unreg() {
        state.value = SipServiceState.Unreg()
        scope.launch {
            userAgent?.unregister()
        }
    }

    override fun registerSuccessful(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            if (userAgent?.isRegistered == true){
                state.value =
                    SipServiceState.RegisteringSuccess()
            } else {
                state.value = SipServiceState.Unreg()
            }
        }
        Log.d(_tag, "registerSuccessful")
    }

    override fun incomingCall(p0: SipRequest?, p1: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            state.value = SipServiceState.IncomingCall(
                p0?.requestUri?.toString() ?: "no data"
            )
        }
        Log.d(_tag, "incomingCall")
        sipRequest = p0
        userAgent?.soundManager?.init()
        donDigidonService.callInvite()
    }

    override fun registerFailed(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            state.value =
                SipServiceState.RegisteringError()
        }
        Log.d(_tag, "registerFailed")
    }

    override fun remoteHangup(p0: SipRequest?) {
        scope.launch(Dispatchers.Main) {
            state.value = SipServiceState.NoActiveState
        }
        userAgent?.soundManager?.close()
        donDigidonService.stopPlaying()
        Log.d(_tag, "remoteHangup")
    }

    override fun ringing(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            state.value = SipServiceState.Ringing("!!")
        }
        userAgent?.soundManager?.init()
        Log.d(_tag, "ringing")
    }

    override fun calleePickup(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            state.value =
                SipServiceState.ActiveConversation(
                    p0?.statusCode?.toString() ?: ""
                )
        }
        //userAgent.close()
        Log.d(_tag, "calleePickup")
    }

    override fun error(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            state.value = SipServiceState.NoActiveState
        }
        donDigidonService.stopPlaying()
        userAgent?.soundManager?.close()
        Log.d(_tag, "error")
    }

    override fun registering(p0: SipRequest?) {
        scope.launch(Dispatchers.Main) {
            state.value = SipServiceState.Registering()
        }
        Log.d(_tag, "registering")
    }


    fun accept() {
        Log.d(_tag, "accept call -> invoke")
        scope.launch {
            if (sipRequest == null) {
                Log.d(_tag, "accept call -> sipRequest is null")
                return@launch
            }
            val callId = Utils.getMessageCallId(sipRequest)
            val dialogManager = userAgent?.dialogManager
            val dialog = dialogManager?.getDialog(callId)
            donDigidonService.stopPlaying()
            userAgent?.acceptCall(sipRequest, dialog)
        }
    }

    fun hangup() {
        scope.launch {
            if (sipRequest == null) {
                Log.d(_tag, "accept call -> sipRequest is null")
                return@launch
            }
            donDigidonService.stopPlaying()
            userAgent?.terminate(sipRequest)
            //sipRequest = null
        }
    }

    fun reject() {
        scope.launch {
            if (sipRequest == null) {
                Log.d(_tag, "accept call -> sipRequest is null")
                return@launch
            }
            userAgent?.soundManager?.close()
            donDigidonService.stopPlaying()
            userAgent?.rejectCall(sipRequest)
        }
    }

    fun call(sipNumber: String) {
        scope.launch {
            userAgent?.soundManager?.init()
            donDigidonService.stopPlaying()
            sipRequest = userAgent?.invite(sipNumber, null)
        }
    }
}