package com.randomgametpnv.sip.entities

sealed class ServiceNotificationType() {

    object Registering: ServiceNotificationType()
    object NoActiveInternetConnection: ServiceNotificationType()
    object StatusOk: ServiceNotificationType()
    object StatusError: ServiceNotificationType()
}