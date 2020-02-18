package com.randomgametpnv.main_screen.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.main_screen.ui.MainScreenViewModel
import com.randomgametpnv.main_screen.ui.utils.MainScreenViewModelFactory
import com.randomgametpnv.navigation.homeFragmentNavId
import org.koin.android.ext.android.inject

open class BaseModuleFragment: Fragment() {

    private val viewModelFactory: MainScreenViewModelFactory by inject()
    lateinit var viewModel: MainScreenViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mainScreenNavId = activity?.homeFragmentNavId()?: throw Throwable("activity is null")

        viewModel =
            navGraphViewModels<MainScreenViewModel>(mainScreenNavId) { viewModelFactory }.value
    }
}