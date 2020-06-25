package com.randomgametpnv.services.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.services.net.ServicesNet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServicesViewModel(private val database: AppDatabase, private val servicesNet: ServicesNet): ViewModel() {


    fun getUseLogin() = liveData {
        val data = withContext(Dispatchers.IO) {database.userDao().getSavedLoginData()?.login?: ""}
        emit(data)
    }
}