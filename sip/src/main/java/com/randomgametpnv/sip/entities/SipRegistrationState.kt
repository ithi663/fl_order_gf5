package com.randomgametpnv.sip.entities

sealed class SipRegistrationState {

    data class StartNewRegistration(val startTime: Long): SipRegistrationState()
    data class Registering(val message: String = "Регистрация"): SipRegistrationState()
    data class RegisteringSuccess(val message: String = "Регистрация прошла успешна"): SipRegistrationState()
    data class RegisteringError(val message: String = "Ошибка регистрации"): SipRegistrationState()
    data class Unreg(val message:String = "Вы больше не зарегистрированы"): SipRegistrationState()
}