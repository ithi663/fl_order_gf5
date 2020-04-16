package com.randomgametpnv.sip.util.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.randomgametpnv.sip.R
import com.randomgametpnv.sip.ui.CallActivity

class AppNotificationFactoryImpl(private val context: Context): AppNotificationFactory {


    private val CHANNEL_ID = "SmartHouse"

    override fun createServiceNotification(): Notification {
        createNotificationChannel()
        val notificationIntent = Intent(context, CallActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            //.setContentTitle("Smart House")
            //.setContentText(input)
            .setSmallIcon(R.drawable.launch_img)
            .setContentIntent(pendingIntent)
            .build()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Smart House service channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context.getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}