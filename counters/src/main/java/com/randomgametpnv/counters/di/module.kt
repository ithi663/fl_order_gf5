package com.randomgametpnv.counters.di

import com.randomgametpnv.common_value_objects.ShareDiEnum
import com.randomgametpnv.counters.net.CountersApi
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.counters.net.CountersNetImpl
import com.randomgametpnv.counters.ui.utils.CountersViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val countersModule = module {

    factory<CountersApi>(named("countersApiCall")) { get<Retrofit>(named(ShareDiEnum.RETROFIT)).create(CountersApi::class.java) }
    factory<CountersNet>(named("countersNet")) { CountersNetImpl(get(named("countersApiCall"))) }

    factory {
        CountersViewModelFactory(
            get(named(ShareDiEnum.DATABASE)),
            get(named("countersNet"))
        )
    }
}