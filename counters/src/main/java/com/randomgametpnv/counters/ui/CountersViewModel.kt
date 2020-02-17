package com.randomgametpnv.counters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomgametpnv.counters.net.CountersNet
import com.randomgametpnv.counters.net.CountersNetImpl
import com.randomgametpnv.database.AppDatabase
import kotlinx.coroutines.launch
import org.koin.ext.scope

class CountersViewModel(private val database: AppDatabase, private val countersNet: CountersNet): ViewModel() {

}