package com.randomgametpnv.order80202.di

import com.randomgametpnv.base.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {


    single<Retrofit>(named("retrofit")) {
        Retrofit.Builder()
            .baseUrl("http://45.84.224.11/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    viewModel { MainViewModel(get(named("database"))) }
}