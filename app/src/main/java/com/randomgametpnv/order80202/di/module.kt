package com.randomgametpnv.order80202.di

import com.randomgametpnv.base.MainViewModel
import com.randomgametpnv.common_value_objects.ShareDiEnum
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = module {




    single<Retrofit>(named(ShareDiEnum.RETROFIT)) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


        Retrofit.Builder()
            .baseUrl("https://ud5-dev.2242000.ru/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    viewModel { MainViewModel(get(named(ShareDiEnum.DATABASE))) }
}