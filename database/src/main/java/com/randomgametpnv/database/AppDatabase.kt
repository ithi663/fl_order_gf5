package com.randomgametpnv.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserData::class, UserRegData::class], version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}