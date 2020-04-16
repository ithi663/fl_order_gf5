package com.randomgametpnv.sip

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.RegisterHandler
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.notifications.AppNotificationFactory
import com.randomgametpnv.sip.util.sip_manager.SipManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.sourceforge.peers.Config
import org.koin.core.KoinComponent
import org.koin.core.inject


class CallService: Service(), KoinComponent {

    private val notificationFactoryFactory: AppNotificationFactory by inject()
    private val networkStateListener: NetworkStateListener by inject()
    private val sipManager: SipManager by inject()
    private val registerHandler: RegisterHandler by inject()
    private val scope: CoroutineScope by inject()

    val binder: LocalBinder = LocalBinder()

    inner class LocalBinder : android.os.Binder() {
        val service: CallService = this@CallService
    }

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, CallService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, CallService::class.java)
            context.stopService(stopIntent)
        }
    }


    fun checkRegistration(config: Config) {
        val state = sipManager.getRegisterListener().value is SipRegistrationState.RegisteringSuccess
        if(!state) {
            scope.launch {
                registerHandler.handleRegistration(config)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = notificationFactoryFactory.createServiceNotification()
        startForeground(mainNotifyId, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}