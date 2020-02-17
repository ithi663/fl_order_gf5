package com.randomgametpnv.counters.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.counters.R
import org.koin.android.ext.android.inject

open class BaseModuleFragment: Fragment() {

    private val viewModelFactory: CountersViewModelFactory by inject()
    lateinit var viewModel: CountersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            navGraphViewModels<CountersViewModel>(R.id.countersFragment) { viewModelFactory }.value
    }
}