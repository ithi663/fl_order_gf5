package com.randomgametpnv.login

import android.util.Log
import androidx.lifecycle.*
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData
import com.randomgametpnv.database.UserRegData
/*import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData*/
import com.randomgametpnv.login.entities.LoginEntities
import com.randomgametpnv.login.entities.LoginState
import com.randomgametpnv.login.net.Net
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val net: Net, private val db: AppDatabase) : ViewModel() {

    private val _result = MutableLiveData<ApiCall<LoginEntities>>()
    val result: LiveData<ApiCall<LoginEntities>> = _result


    fun login(userName: String, pass: String) {

        viewModelScope.launch {
            net.login(userName, pass)
                .map {
                    if (it is ApiCall.Success) {
                        db.userDao().saveLoginData(UserRegData(0, userName, pass))
                        db.userDao().addUser(UserData(0, it.data.accessToken, it.data.tokenType))
                    }
                    it
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = it
                }
        }
    }


    fun checkLogin() = liveData {

        val regData = withContext(Dispatchers.IO) { db.userDao().getSavedLoginData() }

        if (regData == null) {
            emit(LoginState.Logout)
        } else {
            net.login(regData.login, regData.pass)
                .flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is ApiCall.ConnectException -> {
                            emit(LoginState.NoInternet)
                        }
                        is ApiCall.ResponseError -> {
                            emit(LoginState.Logout)
                            withContext(Dispatchers.IO) { db.userDao().deleteLoginData() }
                        }
                        is ApiCall.Success -> {
                            withContext(Dispatchers.IO) {
                                db.userDao()
                                    .addUser(UserData(0, it.data.accessToken, it.data.tokenType))
                            }
                            emit(LoginState.Login(it.data.accessToken, it.data.tokenType))
                        }
                    }
                }
        }
    }
}