package com.randomgametpnv.order80202

import android.app.Application
import com.randomgametpnv.cameras.di.camerasModule
import com.randomgametpnv.counters.di.countersModule
import com.randomgametpnv.database.di.databaseModule
import com.randomgametpnv.help.di.helpModule
import com.randomgametpnv.login.di.loginModule
import com.randomgametpnv.main_screen.di.mainScreenModule
import com.randomgametpnv.order80202.di.mainModule
import com.randomgametpnv.services.di.servicesModule
import com.randomgametpnv.sip.di.sipModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                databaseModule,
                mainModule,
                loginModule,
                camerasModule,
                helpModule,
                servicesModule,
                countersModule,
                mainScreenModule
            ))
        }
    }
}