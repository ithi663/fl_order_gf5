package com.randomgametpnv.counters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.database.AppDatabase

class CountersViewModelFactory (
    private val database: AppDatabase,
    private val net: CountersNet
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AppDatabase::class.java, CountersNet::class.java)
            .newInstance(database, net)
    }
}