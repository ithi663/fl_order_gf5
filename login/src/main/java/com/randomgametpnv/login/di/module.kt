package com.randomgametpnv.login.di

import com.randomgametpnv.login.LoginViewModel
import com.randomgametpnv.login.net.Api
import com.randomgametpnv.login.net.Net
import com.randomgametpnv.login.net.NetImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val loginModule = module {

    single<Api>(named("loginApiCall")) { get<Retrofit>(named("retrofit")).create(Api::class.java) }
    single<Net>(named("loginNet")) { NetImpl(get(named("loginApiCall"))) }

    viewModel { LoginViewModel(get(named("loginNet"))) }
}