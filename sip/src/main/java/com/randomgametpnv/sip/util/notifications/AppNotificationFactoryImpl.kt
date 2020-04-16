package com.randomgametpnv.sip.util.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.randomgametpnv.sip.R
import com.randomgametpnv.sip.messagesNotifyId
import com.randomgametpnv.sip.ui.CallActivity

class AppNotificationFactoryImpl(private val context: Context): AppNotificationFactory {


    private val CHANNEL_ID = "SmartHouse"
    private val pendingIntent: PendingIntent


    init {
        createNotificationChannel()
        val notificationIntent = Intent(context, CallActivity::class.java)
        pendingIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )
    }

    override fun createServiceNotification(): Notification {
        return createNotification("registering..")
    }

    override fun updateNotification(text: String) {
        with(NotificationManagerCompat.from(context)) {
            notify(messagesNotifyId, createNotification(text))
        }
    }


    private fun createNotification(message: String) =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setOnlyAlertOnce(true)
            .setContent(createCustomVIew(message))
            .setSmallIcon(R.drawable.ic_home)
            .setContentIntent(pendingIntent)
            .build()


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Smart House service channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context.getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }



    private fun createCustomVIew(text: String): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.notification_view)
        remoteView.setImageViewResource(R.id.imagenotileft, R.drawable.ic_home)
        remoteView.setTextViewText(R.id.title, "state:")
        remoteView.setTextViewText(R.id.text, text)
        return remoteView
    }
}