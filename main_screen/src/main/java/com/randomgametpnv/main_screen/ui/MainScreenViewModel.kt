package com.randomgametpnv.main_screen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.main_screen.entities.ControllerDataUi
import com.randomgametpnv.main_screen.net.MainScreenNet
import com.randomgametpnv.main_screen.ui.utils.ControllerType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val database: AppDatabase,
    private val mainScreenNet: MainScreenNet
) : ViewModel() {

    private val _networkControllerEvents = MutableLiveData<ApiCall<ControllerDataUi>>()
    val networkControllerEvents: LiveData<ApiCall<ControllerDataUi>> = _networkControllerEvents
    private var requestJob: Job? = null


    fun updateControllerStatus(controllerType: ControllerType, status: Boolean) {
        requestJob?.cancel()
        requestJob = viewModelScope.launch {
            _networkControllerEvents.value = ApiCall.Loading
            delay(1000)
            _networkControllerEvents.value = ApiCall.Success(ControllerDataUi(status.not()))
        }
    }


    fun getControllerRequestStatus() {
        requestJob?.cancel()
        requestJob = viewModelScope.launch {
            _networkControllerEvents.value = ApiCall.Loading
            delay(1000)
            _networkControllerEvents.value = ApiCall.Success(ControllerDataUi(false))
        }
    }
}