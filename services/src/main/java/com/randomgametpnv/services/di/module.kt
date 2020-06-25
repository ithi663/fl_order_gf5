package com.randomgametpnv.services.di

import com.randomgametpnv.common_value_objects.ShareDiEnum
import com.randomgametpnv.services.net.ServicesApi
import com.randomgametpnv.services.net.ServicesNet
import com.randomgametpnv.services.net.ServicesNetImpl
import com.randomgametpnv.services.ui.ServicesViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val servicesModule = module {

    factory<ServicesApi>(named("servicesApiCall")) { get<Retrofit>(named(ShareDiEnum.RETROFIT)).create(ServicesApi::class.java) }
    factory<ServicesNet>(named("servicesNet")) { ServicesNetImpl(get(named("servicesApiCall"))) }

    factory {
        ServicesViewModelFactory(
            get(named(ShareDiEnum.DATABASE)),
            get(named("servicesNet"))
        )
    }
}