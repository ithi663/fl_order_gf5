package com.randomgametpnv.sip.entities

sealed class SipCallState {

    data class IncomingCall(val sipAddress: String): SipCallState()
    //data class Ringing(val sipAddress: String): SipCallState()
    data class ActiveConversation(val sipAddress: String): SipCallState()
    object NoActiveState: SipCallState()
}