package com.randomgametpnv.sip.util.notifications

import android.util.Log
import com.randomgametpnv.sip.entities.NetState
import com.randomgametpnv.sip.entities.ServiceNotificationType
import com.randomgametpnv.sip.entities.SipCallState
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.sip_manager.SipManager

class NotificationMessageHandler(
    private val sipManager: SipManager,
    private val networkStateListener: NetworkStateListener,
    private val appNotificationFactory: AppNotificationFactory
) {


    private var lastRegistrationEvent: SipRegistrationState = SipRegistrationState.Registering()

    init {
        handleRegistrationEvents()
        handleNetworkEvents()
        handleIncomingCallEvents()
    }

    private fun handleNetworkEvents() {
        networkStateListener.getStateListener().observeForever {
            when (it) {
                is NetState.Lose -> {
                    when (lastRegistrationEvent) {
                        is SipRegistrationState.Registering -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.RegisteringWithNoActiveInternetConnection)
                        }
                        else -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.NoActiveInternetConnection)
                        }
                    }
                }
                is NetState.Active -> {
                    when (lastRegistrationEvent) {

                        is SipRegistrationState.Registering -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.RegisteringWithActiveInternetConnection)
                        }

                        is SipRegistrationState.RegisteringError -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.StatusError)
                        }

                        is SipRegistrationState.RegisteringSuccess -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.StatusOk)
                        }
                        else -> {
                            appNotificationFactory.updateServiceNotification(ServiceNotificationType.NoActiveInternetConnection)
                        }
                    }
                }
            }
        }
    }

    private fun handleRegistrationEvents() {
        sipManager.getRegisterListener().observeForever {

            lastRegistrationEvent = it

            when (it) {
                is SipRegistrationState.StartNewRegistration -> { }
                is SipRegistrationState.Registering -> { }
                is SipRegistrationState.Unreg -> { }
                is SipRegistrationState.RegisteringSuccess -> {
                    appNotificationFactory.updateServiceNotification(ServiceNotificationType.StatusOk)
                }
                is SipRegistrationState.RegisteringError -> {
                    appNotificationFactory.updateServiceNotification(ServiceNotificationType.StatusError)
                }
            }
        }
    }


    private fun handleIncomingCallEvents() {

        sipManager.getCallStateListener().observeForever {
            when (it) {
                is SipCallState.NoActiveState -> {
                    appNotificationFactory.hideIncomingCallNotification()
                }
                is SipCallState.ActiveConversation -> {

                }
                is SipCallState.IncomingCall -> {
                    appNotificationFactory.showIncomingCallNotification()
                }
            }
        }
    }
}