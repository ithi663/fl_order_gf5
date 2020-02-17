package com.randomgametpnv.services.di

import com.randomgametpnv.services.net.ServicesApi
import com.randomgametpnv.services.net.ServicesNet
import com.randomgametpnv.services.net.ServicesNetImpl
import com.randomgametpnv.services.ui.ServicesViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val servicesModule = module {

    single<ServicesApi>(named("servicesApiCall")) { get<Retrofit>(named("retrofit")).create(ServicesApi::class.java) }
    single<ServicesNet>(named("servicesNet")) { ServicesNetImpl(get(named("servicesApiCall"))) }

    factory {
        ServicesViewModelFactory(
            get(named("database")),
            get(named("servicesNet"))
        )
    }
}