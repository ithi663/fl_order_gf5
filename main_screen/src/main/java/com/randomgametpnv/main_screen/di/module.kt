package com.randomgametpnv.main_screen.di

import com.randomgametpnv.common_value_objects.ShareDiEnum
import com.randomgametpnv.main_screen.net.MainScreenApi
import com.randomgametpnv.main_screen.net.MainScreenNet
import com.randomgametpnv.main_screen.net.MainScreenNetImpl
import com.randomgametpnv.main_screen.ui.utils.MainScreenViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module {

    single<MainScreenApi>(named("mainScreenApiCall")) { get<Retrofit>(named(ShareDiEnum.RETROFIT)).create(MainScreenApi::class.java) }
    single<MainScreenNet>(named("mainScreenNet")) { MainScreenNetImpl(get(named("mainScreenApiCall"))) }

    factory {
        MainScreenViewModelFactory(
            get(named(ShareDiEnum.DATABASE)),
            get(named("mainScreenNet"))
        )
    }
}