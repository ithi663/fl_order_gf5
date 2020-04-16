package com.randomgametpnv.sip.di

import com.randomgametpnv.sip.util.RegisterHandler
import com.randomgametpnv.sip.util.networkState.NetworkStateListener
import com.randomgametpnv.sip.util.networkState.NetworkStateListenerImpl
import com.randomgametpnv.sip.util.notifications.AppNotificationFactory
import com.randomgametpnv.sip.util.notifications.AppNotificationFactoryImpl
import com.randomgametpnv.sip.util.sip_manager.SipManager
import com.randomgametpnv.sip.util.sip_manager.SipManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sipModule = module {

    single { CoroutineScope(Dispatchers.IO + Job()) }
    single<SipManager> { SipManagerImpl(androidContext(), get()) }
    single<AppNotificationFactory> { AppNotificationFactoryImpl(androidContext()) }
    single<NetworkStateListener> { NetworkStateListenerImpl(androidContext(), get()) }
    single { RegisterHandler(get(), get()) }
}