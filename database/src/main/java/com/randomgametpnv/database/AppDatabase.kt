package com.randomgametpnv.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserData::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}