package com.randomgametpnv.sip

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.randomgametpnv.sip.entities.ServiceNotificationType
import com.randomgametpnv.sip.entities.SipRegistrationState
import com.randomgametpnv.sip.util.RegistrationHandler
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.networkState.NetworkStateListenerImpl
import com.randomgametpnv.sip.util.notifications.AppNotificationFactory
import com.randomgametpnv.sip.util.notifications.AppNotificationFactoryImpl
import com.randomgametpnv.sip.util.notifications.NotificationMessageHandler
import com.randomgametpnv.sip.util.sip_manager.SipManager
import com.randomgametpnv.sip.util.sip_manager.SipManagerImpl
import kotlinx.coroutines.*
import net.sourceforge.peers.Config
import org.koin.core.KoinComponent


class CallService: Service(), KoinComponent {


    private lateinit var scope: CoroutineScope
    private lateinit var config: CompletableDeferred<Config>
    private lateinit var networkStateListener: NetworkStateListener
    private lateinit var notificationFactory: AppNotificationFactory
    private lateinit var notificationMessageHandler: NotificationMessageHandler
    private lateinit var sipManager: SipManager
    private lateinit var registrationHandler: RegistrationHandler

    private var wakeLock: PowerManager.WakeLock? = null

    val binder: LocalBinder = LocalBinder()

    private fun initAllValues() {

        scope = CoroutineScope(Dispatchers.IO + Job())
        config = CompletableDeferred()
        notificationFactory = AppNotificationFactoryImpl(this)
        sipManager = SipManagerImpl(this, scope)
        registrationHandler = RegistrationHandler(sipManager, scope, config)
        networkStateListener = NetworkStateListenerImpl(this, scope, registrationHandler)
        notificationMessageHandler = NotificationMessageHandler(sipManager, networkStateListener, notificationFactory, registrationHandler)
    }

    inner class LocalBinder : android.os.Binder() {
        val service: CallService = this@CallService
    }


    override fun onCreate() {
        super.onCreate()

        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHouse::lock").apply {
                    acquire()
                }
            }

        initAllValues()
        val notification = notificationFactory.showServiceNotification(ServiceNotificationType.Registering)
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
    fun endCall() = sipManager.endCall()

    fun checkRegistration(userConfig: Config) {
        config.complete(userConfig)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}