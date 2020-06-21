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

@Entity(tableName = "login_data")
data class UserRegData(
    @PrimaryKey
    var id: Int = 0,
    var login: String,
    var pass: String
)