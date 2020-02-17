package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.help.R
import org.koin.android.ext.android.inject


class VotingFragment : Fragment() {

    private val viewModelFactory: HelpViewModelFactory by inject()
    lateinit var viewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            navGraphViewModels<HelpViewModel>(R.id.helpFragment) { viewModelFactory }.value

        val topText = resources.getText(com.randomgametpnv.base.R.string.voting).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)
    }

}
