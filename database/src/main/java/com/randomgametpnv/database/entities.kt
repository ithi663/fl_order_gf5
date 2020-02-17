package com.randomgametpnv.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey
    var id:Int = 0,
    var accessToken: String,
    var tokenType: String
)