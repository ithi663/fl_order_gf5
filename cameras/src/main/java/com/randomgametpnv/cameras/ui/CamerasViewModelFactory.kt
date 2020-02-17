package com.randomgametpnv.cameras.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomgametpnv.cameras.net.CameraNet
import com.randomgametpnv.database.AppDatabase

class CamerasViewModelFactory (
    private val database: AppDatabase,
    private val net: CameraNet
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java, CameraNet::class.java)
            .newInstance(database, net)
    }
}