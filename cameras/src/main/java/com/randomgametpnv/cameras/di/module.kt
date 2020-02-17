package com.randomgametpnv.cameras.di

import com.randomgametpnv.cameras.ui.CamerasViewModelFactory
import com.randomgametpnv.cameras.net.CameraApi
import com.randomgametpnv.cameras.net.CameraNet
import com.randomgametpnv.cameras.net.NetImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val camerasModule = module {

    single<CameraApi>(named("camerasApiCall")) { get<Retrofit>(named("retrofit")).create(CameraApi::class.java) }
    single<CameraNet>(named("camerasNet")) { NetImpl(get(named("camerasApiCall"))) }

    factory {
        CamerasViewModelFactory(
            get(named("database")),
            get(named("camerasNet"))
        )
    }
}