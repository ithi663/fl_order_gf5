package com.randomgametpnv.database.di

import androidx.room.Room
import com.randomgametpnv.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {

    single(named("database")) {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "AppDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }
}