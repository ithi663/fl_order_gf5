package com.randomgametpnv.sip.di

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
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sipModule = module {

    single { CoroutineScope(Dispatchers.IO + Job()) }
    single{ CompletableDeferred<Config>() }
    single<SipManager> { SipManagerImpl(androidContext(), get()) }
    single<AppNotificationFactory> { AppNotificationFactoryImpl(androidContext()) }
    single<NetworkStateListener> { NetworkStateListenerImpl(androidContext(), get(), get()) }
    single { RegistrationHandler(get(), get(), get()) }
    single { NotificationMessageHandler(get(), get(), get(), get()) }
}