package com.randomgametpnv.sip.util.sip_manager

import androidx.lifecycle.LiveData
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.entities.SipCallState
import net.sourceforge.peers.Config

interface SipManager {

    fun getCallStateListener(): LiveData<SipCallState>
    fun getRegisterListener(): LiveData<SipRegistrationState>

    fun register(config: Config): LiveData<SipRegistrationState>
    fun unregister()

    fun accept()
    fun reject()
    fun hangup()
}
