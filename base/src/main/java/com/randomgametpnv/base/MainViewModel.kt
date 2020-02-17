package com.randomgametpnv.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val database: AppDatabase) : ViewModel() {


    fun saveUserData(accessToken: String, tokenType: String) {

        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().addUser(UserData(0, accessToken, tokenType))
        }
    }
}