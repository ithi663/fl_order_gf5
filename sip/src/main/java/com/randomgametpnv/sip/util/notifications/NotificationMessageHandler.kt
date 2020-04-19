package com.randomgametpnv.sip.util.notifications

import android.util.Log
import com.randomgametpnv.sip.entities.NetState
import com.randomgametpnv.sip.entities.SipCallState
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.sip_manager.SipManager

class NotificationMessageHandler(
    private val sipManager: SipManager,
    private val networkStateListener: NetworkStateListener,
    private val appNotificationFactory: AppNotificationFactory
) {


    var lastRegistrationEvent: String = ""

    init {
        handleRegistrationEvents()
        handleNetworkEvents()
        handleIncomingCallEvents()
    }

    private fun handleNetworkEvents() {
        networkStateListener.getStateListener().observeForever {
            when (it) {
                is NetState.Lose -> {
                    appNotificationFactory.updateServiceNotification("отсутствует интернет соединение")
                }
                is NetState.Active -> {
                    appNotificationFactory.updateServiceNotification(lastRegistrationEvent)
                }
            }
        }
    }

    private fun handleRegistrationEvents() {
        sipManager.getRegisterListener().observeForever {
            when (it) {
                is SipRegistrationState.StartNewRegistration -> {
                }
                is SipRegistrationState.Registering -> {
                    lastRegistrationEvent = "регистрация.."
                    appNotificationFactory.updateServiceNotification("регистрация..")
                }
                is SipRegistrationState.RegisteringSuccess -> {
                    lastRegistrationEvent = "регистрация прошла успешно"
                    appNotificationFactory.updateServiceNotification("регистрация прошла успешно")
                }
                is SipRegistrationState.RegisteringError -> {
                    lastRegistrationEvent = "ошибка регистрации"
                    appNotificationFactory.updateServiceNotification("ошибка регистрации")
                }
                is SipRegistrationState.Unreg -> {
                }
            }
        }
    }


    private fun handleIncomingCallEvents() {

        sipManager.getCallStateListener().observeForever {
            when(it) {
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