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
import com.randomgametpnv.sip.entities.ServiceNotificationType
import com.randomgametpnv.sip.mainNotifyId
import com.randomgametpnv.sip.incomingCallNotifyId
import com.randomgametpnv.sip.ui.CallActivity

class AppNotificationFactoryImpl(private val context: Context): AppNotificationFactory {


    private val MAIN_CHANNEL_ID = "SmartHouseService"
    private val INCOMING_CALL_CHANNEL_ID = "SmartHouseCall"
    private val MISSING_CALL_CHANNEL_ID = "SmartHouseMissingCalls"

    private val notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val serviceMainNotificationVIewFactory: ServiceMainNotificationVIewFactory = ServiceMainNotificationVIewFactory(context)

    private lateinit var servicePendingIntent: PendingIntent
    private lateinit var incomingCallPendingIntent: PendingIntent
    private lateinit var missingCallPendingIntent: PendingIntent

    init {
        prepareServiceNotifications()
        prepareIncomingCallNotifications()
        prepareMissingCallNotifications()
    }

    override fun showServiceNotification(notificationType: ServiceNotificationType): Notification {
        return createServiceNotification(notificationType)
    }

    override fun updateServiceNotification(notificationType: ServiceNotificationType) {
        notificationManagerCompat.notify(mainNotifyId, createServiceNotification(notificationType))
    }

    override fun showIncomingCallNotification() {

        val notification = NotificationCompat.Builder(context, INCOMING_CALL_CHANNEL_ID)
            .setOnlyAlertOnce(true)
            //.setContent(createCallCustomView())
            .setSmallIcon(R.drawable.ic_home)
            .setContentTitle(context.getString(R.string.incomint_call_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setFullScreenIntent(incomingCallPendingIntent, true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManagerCompat.notify(incomingCallNotifyId, notification)
    }

    override fun hideIncomingCallNotification() {
        notificationManagerCompat.cancel(incomingCallNotifyId)
    }

    override fun showMissingCallNotification() {

        val notification = NotificationCompat.Builder(context, INCOMING_CALL_CHANNEL_ID)
            .setOnlyAlertOnce(true)
            //.setContent(createCallCustomView())
            .setSmallIcon(R.drawable.ic_home)
            .setContentTitle("Пропущенный вызов")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setFullScreenIntent(missingCallPendingIntent, true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManagerCompat.notify(incomingCallNotifyId, notification)
    }


    private fun createServiceNotification(notificationType: ServiceNotificationType) =
        NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
            .setOnlyAlertOnce(true)
            .setContent(serviceMainNotificationVIewFactory.getView(notificationType))
            .setSmallIcon(R.drawable.ic_home)
            .setContentIntent(servicePendingIntent)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()


    private fun prepareServiceNotifications() {

        val serviceNotificationIntent = Intent(context, CallActivity::class.java)
        servicePendingIntent = PendingIntent.getActivity(
            context,
            0, serviceNotificationIntent, 0
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(MAIN_CHANNEL_ID, "Smart House service channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManagerCompat.createNotificationChannel(serviceChannel)
        }
    }


    private fun prepareIncomingCallNotifications() {

        val callNotificationIntent = Intent(context, CallActivity::class.java)
        incomingCallPendingIntent = PendingIntent.getActivity(
            context,
            0, callNotificationIntent, 0
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val callChannel = NotificationChannel(INCOMING_CALL_CHANNEL_ID, "Smart House call channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManagerCompat.createNotificationChannel(callChannel)
        }
    }

    private fun prepareMissingCallNotifications() {

        val missingCallNotificationIntent = Intent(context, CallActivity::class.java)
        missingCallPendingIntent = PendingIntent.getActivity(
            context,
            0, missingCallNotificationIntent, 0
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val callChannel = NotificationChannel(MISSING_CALL_CHANNEL_ID, "Smart House missing call channel",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManagerCompat.createNotificationChannel(callChannel)
        }
    }


    private fun createCallCustomView(): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.notification_view)
        remoteView.setImageViewResource(R.id.imagenotileft, R.drawable.ic_home)
        remoteView.setTextViewText(R.id.title, "state:")
        remoteView.setTextViewText(R.id.text, "text")
        return remoteView
    }
}