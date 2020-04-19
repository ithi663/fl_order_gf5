package com.randomgametpnv.sip.util.notifications

import android.app.Notification

interface AppNotificationFactory {

    fun showServiceNotification(): Notification
    fun updateServiceNotification(text: String)
    fun showIncomingCallNotification()
    fun hideIncomingCallNotification()
    fun showMissingCallNotification()
}