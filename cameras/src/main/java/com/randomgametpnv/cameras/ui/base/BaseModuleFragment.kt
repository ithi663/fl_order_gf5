package com.randomgametpnv.cameras.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.cameras.R
import com.randomgametpnv.cameras.ui.CamerasViewModel
import com.randomgametpnv.cameras.ui.utils.CamerasViewModelFactory
import org.koin.android.ext.android.inject

open class BaseModuleFragment: Fragment() {

    val viewModelFactory: CamerasViewModelFactory by inject()
    lateinit var viewModel: CamerasViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel =
            navGraphViewModels<CamerasViewModel>(R.id.cameraGroupsFragment) { viewModelFactory }.value
    }
}