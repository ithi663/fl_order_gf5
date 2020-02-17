package com.randomgametpnv.help.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.help.R
import org.koin.android.ext.android.inject

open class BaseModuleFragment: Fragment() {

    private val viewModelFactory: HelpViewModelFactory by inject()
    lateinit var viewModel: HelpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            navGraphViewModels<HelpViewModel>(R.id.helpFragment) { viewModelFactory }.value

    }
}