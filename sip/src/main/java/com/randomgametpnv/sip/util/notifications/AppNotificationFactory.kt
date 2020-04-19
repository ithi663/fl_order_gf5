package com.randomgametpnv.sip.util.notifications

import android.app.Notification
import com.randomgametpnv.sip.entities.ServiceNotificationType

interface AppNotificationFactory {

    fun showServiceNotification(notificationType: ServiceNotificationType): Notification
    fun updateServiceNotification(notificationType: ServiceNotificationType)
    fun showIncomingCallNotification()
    fun hideIncomingCallNotification()
    fun showMissingCallNotification()
}