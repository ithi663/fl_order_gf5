package com.randomgametpnv.help.di

import com.randomgametpnv.common_value_objects.ShareDiEnum
import com.randomgametpnv.help.net.HelpApi
import com.randomgametpnv.help.net.HelpNet
import com.randomgametpnv.help.net.HelpNetImpl
import com.randomgametpnv.help.ui.util.HelpViewModelFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val helpModule = module {

    factory<HelpApi>(named(DiHelper.HELP_API_CALL)) { get<Retrofit>(named(ShareDiEnum.RETROFIT)).create(HelpApi::class.java) }
    factory<HelpNet>(named(DiHelper.HELP_NET)) { HelpNetImpl(get(named(DiHelper.HELP_API_CALL))) }

    factory {
        HelpViewModelFactory(
            get(named(ShareDiEnum.DATABASE)),
            get(named(DiHelper.HELP_NET))
        )
    }
}

enum class DiHelper {
    HELP_API_CALL,
    HELP_NET
}