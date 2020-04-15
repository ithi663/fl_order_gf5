package com.randomgametpnv.sip.entities

sealed class SipServiceState {

    data class Registering(val message: String = "Регистрация"): SipServiceState()
    data class RegisteringSuccess(val message: String = "Регистрация прошла успешна"): SipServiceState()
    data class RegisteringError(val message: String = "Ошибка регистрации"): SipServiceState()
    data class Unreg(val message:String = "Вы больше не зарегистрированы"): SipServiceState()

    data class IncomingCall(val sipAddress: String): SipServiceState()
    data class Ringing(val sipAddress: String): SipServiceState()
    data class ActiveConversation(val sipAddress: String): SipServiceState()
    object NoActiveState: SipServiceState()
}