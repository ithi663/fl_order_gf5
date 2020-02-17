package com.randomgametpnv.main_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.main_screen.net.MainScreenNet

class MainScreenViewModelFactory (
    private val database: AppDatabase,
    private val net: MainScreenNet
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java, MainScreenNet::class.java)
            .newInstance(database, net)
    }
}