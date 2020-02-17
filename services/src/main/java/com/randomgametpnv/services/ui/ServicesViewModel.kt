package com.randomgametpnv.services.ui

import androidx.lifecycle.ViewModel
import com.randomgametpnv.database.AppDatabase
import com.randomgametpnv.services.net.ServicesNet

class ServicesViewModel(private val database: AppDatabase, private val servicesNet: ServicesNet): ViewModel() {
}