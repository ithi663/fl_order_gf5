package com.randomgametpnv.main_screen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.main_screen.entities.ControllerDataUi
import com.randomgametpnv.main_screen.net.MainScreenNet
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import com.randomgametpnv.main_screen.ui.utils.Status

class MainScreenViewModel(private val database: AppDatabase, private val mainScreenNet: MainScreenNet): ViewModel() {

    private val _powerRes = MutableLiveData<ApiCall<ControllerDataUi>>()
    val powerRes: LiveData<ApiCall<ControllerDataUi>> = _powerRes

    private val _secureRes = MutableLiveData<ApiCall<ControllerDataUi>>()
    val secureRes: LiveData<ApiCall<ControllerDataUi>> = _secureRes


    fun updateControllerStatus(controllerType: ControllerType, status: Boolean) {

    }

    fun getPowerStatus() {
        _powerRes.value = ApiCall.Loading
    }

    fun getSecurityStatus() {
        _secureRes.value = ApiCall.Loading
    }
}