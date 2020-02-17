package com.randomgametpnv.main_screen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.main_screen.entities.ControllerDataUi
import com.randomgametpnv.main_screen.net.MainScreenNet

class MainScreenViewModel(private val database: AppDatabase, private val mainScreenNet: MainScreenNet): ViewModel() {

    private val _powerRes = MutableLiveData<ApiCall<ControllerDataUi>>()
    val powerRes = _powerRes

    private val _secureRes = MutableLiveData<ApiCall<ControllerDataUi>>()
    val secureRes = _secureRes


    fun getPowerStatus() {

    }

    fun getSecureStatus() {

    }
}