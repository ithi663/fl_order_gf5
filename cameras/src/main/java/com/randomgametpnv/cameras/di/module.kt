package com.randomgametpnv.cameras.di

import com.randomgametpnv.cameras.ui.utils.CamerasViewModelFactory
import com.randomgametpnv.cameras.net.CameraApi
import com.randomgametpnv.cameras.net.CameraNet
import com.randomgametpnv.cameras.net.NetImpl
import com.randomgametpnv.common_value_objects.ShareDiEnum
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val camerasModule = module {

    factory<CameraApi>(named("camerasApiCall")) { get<Retrofit>(named(ShareDiEnum.RETROFIT)).create(CameraApi::class.java) }
    factory<CameraNet>(named("camerasNet")) { NetImpl(get(named("camerasApiCall"))) }

    factory {
        CamerasViewModelFactory(
            get(named(ShareDiEnum.DATABASE)),
            get(named("camerasNet"))
        )
    }
}