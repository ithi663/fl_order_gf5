package com.randomgametpnv.sip.entities

sealed class ServiceNotificationType() {

    object RegisteringWithActiveInternetConnection: ServiceNotificationType()
    object RegisteringWithNoActiveInternetConnection: ServiceNotificationType()
    object NoActiveInternetConnection: ServiceNotificationType()
    object StatusOk: ServiceNotificationType()
    object StatusError: ServiceNotificationType()
}