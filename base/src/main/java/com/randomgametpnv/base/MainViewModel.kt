package com.randomgametpnv.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData
import com.randomgametpnv.database.UserRegData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val database: AppDatabase) : ViewModel() {

    fun saveUserData(accessToken: String, tokenType: String) {

        viewModelScope.launch(Dispatchers.IO) {
            database.userDao().addUser(UserData(0, accessToken, tokenType))
        }
    }

    fun saveRegData(userLogin: String?, userPass: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            userLogin?: return@launch
            userPass?: return@launch
            val dbData = UserRegData(0, userLogin, userPass)
            database.userDao().saveLoginData(dbData)
        }
    }

    fun deleteLoginData() = liveData {
        withContext(Dispatchers.IO) {database.userDao().deleteLoginData()}
        emit(true);
    }
}