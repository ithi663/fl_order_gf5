package com.randomgametpnv.sip.util.notifications

import com.randomgametpnv.sip.entities.NetState
import com.randomgametpnv.sip.entities.ServiceNotificationType
import com.randomgametpnv.sip.entities.SipCallState
import com.randomgametpnv.sip.util.RegistrationHandler
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.sip_manager.SipManager

class NotificationMessageHandler(
    private val sipManager: SipManager,
    private val networkStateListener: NetworkStateListener,
    private val appNotificationFactory: AppNotificationFactory,
    private val registrationHandler: RegistrationHandler
) {

    private var lastRegistrationEvent: ServiceNotificationType = ServiceNotificationType.Registering

    init {
        handleRegistrationEvents()
        handleNetworkEvents()
        handleIncomingCallEvents()
    }

    private fun handleNetworkEvents() {
        networkStateListener.getStateListener().observeForever {
            when (it) {
                is NetState.Lose -> {
                    appNotificationFactory.updateServiceNotification(ServiceNotificationType.NoActiveInternetConnection)
                }

                is NetState.Active -> {
                    appNotificationFactory.updateServiceNotification(lastRegistrationEvent)
                }
            }
        }
    }

    private fun handleRegistrationEvents() {
        registrationHandler.events.observeForever {
            appNotificationFactory.updateServiceNotification(it)
        }
    }


    private fun handleIncomingCallEvents() {

        sipManager.getCallStateListener().observeForever {
            when(it) {
                is SipCallState.NoActiveState -> {
                    appNotificationFactory.hideIncomingCallNotification()
                }
                is SipCallState.ActiveConversation -> { }
                is SipCallState.IncomingCall -> {
                    appNotificationFactory.showIncomingCallNotification()
                }
            }
        }
    }
}