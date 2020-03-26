package com.randomgametpnv.cameras.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.base.createRequestHeader
import com.randomgametpnv.cameras.entities.CameraDataUi
import com.randomgametpnv.cameras.net.CameraNet
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CamerasViewModel(private val database: AppDatabase, private val net: CameraNet): ViewModel() {

    private val _res = MutableLiveData<ApiCall<List<CameraDataUi>>>()
    val res: LiveData<ApiCall<List<CameraDataUi>>> = _res

    init {
        viewModelScope.launch {
            val header = withContext(Dispatchers.IO) {database.userDao().getUser().createRequestHeader()}
            header?.let {
                net.getAllCameras(it)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        _res.value = it
                    }
            }
        }
    }

}