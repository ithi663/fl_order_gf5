package com.randomgametpnv.sip.util.notifications

import android.content.Context
import android.view.View
import android.widget.RemoteViews
import com.randomgametpnv.sip.R
import com.randomgametpnv.sip.entities.ServiceNotificationType

class ServiceMainNotificationVIewFactory(val context: Context) {


    fun getView(serviceNotificationType: ServiceNotificationType): RemoteViews {
       return when(serviceNotificationType) {
            is ServiceNotificationType.Registering -> {
               createServiceCustomVIew(null, true)
           }
            is ServiceNotificationType.NoActiveInternetConnection -> {
                createServiceCustomVIew(R.drawable.ic_no_internet_connection, false)

            }
            is ServiceNotificationType.StatusOk -> {
                createServiceCustomVIew(R.drawable.ic_done_black_24dp, false)

            }
            is ServiceNotificationType.StatusError -> {
                createServiceCustomVIew(R.drawable.ic_error_outline_black_24dp, false)
            }
        }
    }

    private fun createServiceCustomVIew(resourceId: Int?, visibilityOfProgress: Boolean): RemoteViews {

        val remoteView = RemoteViews(context.packageName, R.layout.notification_view)
        remoteView.setImageViewResource(R.id.imagenotileft, R.drawable.ic_home)
        if (resourceId != null) { remoteView.setImageViewResource(R.id.notifyImg, resourceId) }
        if (!visibilityOfProgress) remoteView.setViewVisibility(R.id.register_progress, View.GONE)
        return remoteView
    }
}

