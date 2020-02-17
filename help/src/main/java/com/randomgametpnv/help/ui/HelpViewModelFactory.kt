package com.randomgametpnv.help.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.help.net.HelpNet

class HelpViewModelFactory (
    private val database: AppDatabase,
    private val net: HelpNet
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java, HelpNet::class.java)
            .newInstance(database, net)
    }
}