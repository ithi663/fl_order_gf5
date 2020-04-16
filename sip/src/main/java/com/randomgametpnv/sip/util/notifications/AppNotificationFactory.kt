package com.randomgametpnv.sip.util.notifications

import android.app.Notification

interface AppNotificationFactory {

    fun createServiceNotification(): Notification
    fun updateNotification(text: String)
}