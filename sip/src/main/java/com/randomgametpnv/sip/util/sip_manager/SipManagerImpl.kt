package com.randomgametpnv.sip.util.sip_manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.randomgametpnv.sip.entities.SipCallState
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.DonDigidonHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.sourceforge.peers.Config
import net.sourceforge.peers.FileLogger
import net.sourceforge.peers.Logger
import net.sourceforge.peers.sip.Utils
import net.sourceforge.peers.sip.core.useragent.SipListener
import net.sourceforge.peers.sip.core.useragent.UserAgent
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException
import net.sourceforge.peers.sip.transport.SipRequest
import net.sourceforge.peers.sip.transport.SipResponse

class SipManagerImpl(val context: Context, private val scope: CoroutineScope) : SipListener, SipManager {

    private val _tag = "SIP_TAG"

    private val _callState = MutableLiveData<SipCallState>()
    private val _sipRegistrationState = MutableLiveData<SipRegistrationState>()
    override fun getCallStateListener() = _callState
    override fun getRegisterListener() = _sipRegistrationState

    private var userAgent: UserAgent? = null
    private var donDigidonService = DonDigidonHandler(context)
    private var javaxSoundManager: SipAudioManager = SipAudioManager()
    private var sipRequest: SipRequest? = null


    init {
        _sipRegistrationState.value = SipRegistrationState.Unreg()
    }

    override fun register(config: Config): LiveData<SipRegistrationState> {

        scope.launch {
            try {
                withContext(Dispatchers.Main) {_sipRegistrationState.value = SipRegistrationState.StartNewRegistration(System.currentTimeMillis())}
                Log.d(_tag, "registration start")
                val logger: Logger = FileLogger(null)
                userAgent = UserAgent(this@SipManagerImpl, config, logger, javaxSoundManager)
                userAgent?.register()

            } catch (e: SipUriSyntaxException) {
                withContext(Dispatchers.Main) {_sipRegistrationState.value = SipRegistrationState.RegisteringError()}
                Log.d(_tag, "register userAgent error -> $e")
            }
        }
        return _sipRegistrationState
    }

    override fun unregister() {
        _sipRegistrationState.value = SipRegistrationState.Unreg()
        scope.launch {
            userAgent?.unregister()
        }
    }



    override fun registerSuccessful(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            if (userAgent?.isRegistered == true){
                _sipRegistrationState.value = SipRegistrationState.RegisteringSuccess()
            } else {
                _sipRegistrationState.value = SipRegistrationState.Unreg()
            }
        }
        Log.d(_tag, "registerSuccessful")
    }

    override fun registerFailed(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            _sipRegistrationState.value = SipRegistrationState.RegisteringError()
        }
        Log.d(_tag, "registerFailed")
    }

    override fun registering(p0: SipRequest?) {
        scope.launch(Dispatchers.Main) {
            _sipRegistrationState.value = SipRegistrationState.Registering()
        }
        Log.d(_tag, "registering")
    }


    override fun incomingCall(p0: SipRequest?, p1: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            _callState.value = SipCallState.IncomingCall(
                p0?.requestUri?.toString() ?: "no data"
            )
        }
        Log.d(_tag, "incomingCall")
        sipRequest = p0
        userAgent?.soundManager?.init()
        donDigidonService.callInvite()
    }

    override fun remoteHangup(p0: SipRequest?) {
        scope.launch(Dispatchers.Main) {
            _callState.value = SipCallState.NoActiveState
        }
        userAgent?.soundManager?.close()
        donDigidonService.stopPlaying()
        Log.d(_tag, "remoteHangup")
    }

    override fun ringing(p0: SipResponse?) {
/*        scope.launch(Dispatchers.Main) {
            _state.value = SipCallState.Ringing("!!")
        }*/
        userAgent?.soundManager?.init()
        Log.d(_tag, "ringing")
    }

    override fun calleePickup(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            _callState.value =
                SipCallState.ActiveConversation(
                    p0?.statusCode?.toString() ?: ""
                )
        }
        Log.d(_tag, "calleePickup")
    }

    override fun error(p0: SipResponse?) {
        scope.launch(Dispatchers.Main) {
            _callState.value = SipCallState.NoActiveState
        }
        donDigidonService.stopPlaying()
        userAgent?.soundManager?.close()
        Log.d(_tag, "error")
    }


    override fun accept() {
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

    override fun hangup() {
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

    override fun reject() {
        scope.launch {
            if (sipRequest == null) {
                Log.d(_tag, "accept call -> sipRequest is null")
                return@launch
            }
            withContext(Dispatchers.Main) {_callState.value = SipCallState.NoActiveState}
            userAgent?.soundManager?.close()
            donDigidonService.stopPlaying()
            userAgent?.rejectCall(sipRequest)
        }
    }

/*    fun call(sipNumber: String) {
        scope.launch {
            userAgent?.soundManager?.init()
            donDigidonService.stopPlaying()
            sipRequest = userAgent?.invite(sipNumber, null)
        }
    }*/
}