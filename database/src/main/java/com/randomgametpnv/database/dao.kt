package com.randomgametpnv.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * FROM user_data")
    fun getUser(): UserData?

    @Query("SELECT * FROM user_data")
    fun getUserFlow(): Flow<UserData>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserData(user: UserData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserData)
}