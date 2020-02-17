package com.randomgametpnv.services.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.services.R
import org.koin.android.ext.android.inject

open class BaseModuleFragment: Fragment() {

    private val viewModelFactory: ServicesViewModelFactory by inject()
    lateinit var viewModel: ServicesViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            navGraphViewModels<ServicesViewModel>(R.id.servicesFragment) { viewModelFactory }.value
    }
}