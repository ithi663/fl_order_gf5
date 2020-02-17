package com.randomgametpnv.help.di

import com.randomgametpnv.help.net.HelpApi
import com.randomgametpnv.help.net.HelpNet
import com.randomgametpnv.help.net.HelpNetImpl
import com.randomgametpnv.help.ui.HelpViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val helpModule = module {

    single<HelpApi>(named("helpApiCall")) { get<Retrofit>(named("retrofit")).create(HelpApi::class.java) }
    single<HelpNet>(named("helpNet")) { HelpNetImpl(get(named("helpApiCall"))) }

    factory {
        HelpViewModelFactory(
            get(named("database")),
            get(named("helpNet"))
        )
    }
}