package com.randomgametpnv.services.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.services.net.ServicesNet

class ServicesViewModelFactory (
    private val database: AppDatabase,
    private val net: ServicesNet
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java, ServicesNet::class.java)
            .newInstance(database, net)
    }
}