package com.randomgametpnv.main_screen.di

import com.randomgametpnv.main_screen.net.MainScreenApi
import com.randomgametpnv.main_screen.net.MainScreenNet
import com.randomgametpnv.main_screen.net.MainScreenNetImpl
import com.randomgametpnv.main_screen.ui.MainScreenViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module {

    single<MainScreenApi>(named("mainScreenApiCall")) { get<Retrofit>(named("retrofit")).create(MainScreenApi::class.java) }
    single<MainScreenNet>(named("mainScreenNet")) { MainScreenNetImpl(get(named("mainScreenApiCall"))) }

    factory {
        MainScreenViewModelFactory(
            get(named("database")),
            get(named("mainScreenNet"))
        )
    }
}