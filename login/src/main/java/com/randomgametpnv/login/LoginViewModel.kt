package com.randomgametpnv.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.common_value_objects.ApiCall
/*import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.database.UserData*/
import com.randomgametpnv.login.entities.LoginEntities
import com.randomgametpnv.login.net.Net
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val net: Net): ViewModel() {

    private val _result = MutableLiveData<ApiCall<LoginEntities>>()
    val result: LiveData<ApiCall<LoginEntities>> = _result



    fun login(userName: String, pass: String) {

        viewModelScope.launch {
            net.login(userName, pass)
                .flowOn(Dispatchers.IO)
                .collect {
                    _result.value = it
            }
        }
    }
}