package com.randomgametpnv.sip

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.RegisterHandler
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.networkState.NetworkStateListenerImpl
import com.randomgametpnv.sip.util.notifications.AppNotificationFactory
import com.randomgametpnv.sip.util.notifications.AppNotificationFactoryImpl
import com.randomgametpnv.sip.util.notifications.NotificationMessageHandler
import com.randomgametpnv.sip.util.sip_manager.SipManager
import com.randomgametpnv.sip.util.sip_manager.SipManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.sourceforge.peers.Config
import org.koin.core.KoinComponent


class CallService: Service(), KoinComponent {


    private lateinit var scope: CoroutineScope
    private lateinit var networkStateListener: NetworkStateListener
    private lateinit var notificationFactory: AppNotificationFactory
    private lateinit var notificationMessageHandler: NotificationMessageHandler
    private lateinit var sipManager: SipManager
    private lateinit var registerHandler: RegisterHandler

    val binder: LocalBinder = LocalBinder()

    private fun initAllValues() {

        scope = CoroutineScope(Dispatchers.IO + Job())
        notificationFactory = AppNotificationFactoryImpl(this)
        sipManager = SipManagerImpl(this, scope)
        networkStateListener = NetworkStateListenerImpl(this, scope)
        registerHandler = RegisterHandler(networkStateListener, sipManager)
        notificationMessageHandler = NotificationMessageHandler(sipManager, networkStateListener, notificationFactory)
    }

    inner class LocalBinder : android.os.Binder() {
        val service: CallService = this@CallService
    }


    override fun onCreate() {
        super.onCreate()

        initAllValues()
        val notification = notificationFactory.showServiceNotification()
        startForeground(mainNotifyId, notification)
    }

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, CallService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startIntent)
                return
            }
            context.startService(startIntent)
        }

    }

    fun getCallEvents() = sipManager.getCallStateListener()
    fun pickUp() = sipManager.accept()
    fun reject() = sipManager.reject()

    fun openDoor() { TODO() }

    fun checkRegistration(config: Config) {

        val state = sipManager.getRegisterListener().value is SipRegistrationState.RegisteringSuccess
        if(!state) {
            scope.launch {
                registerHandler.handleRegistration(config)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}