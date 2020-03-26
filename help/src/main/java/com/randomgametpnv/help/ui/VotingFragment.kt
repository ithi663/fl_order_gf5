package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import com.randomgametpnv.help.ui.util.HelpViewModelFactory
import org.koin.android.ext.android.inject


class VotingFragment : BaseModuleFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topText = resources.getText(com.randomgametpnv.base.R.string.voting).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)
    }

}
