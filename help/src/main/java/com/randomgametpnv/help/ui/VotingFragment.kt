package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.base.showErrorMessage
import com.randomgametpnv.common_value_objects.ApiCall
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.adapter.VotingPageAdapter
import com.randomgametpnv.help.ui.base.BaseModuleFragment
import com.randomgametpnv.help.ui.util.HelpViewModelFactory
import kotlinx.android.synthetic.main.fragment_voting.*
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


        voteViewPager.adapter = VotingPageAdapter(viewModel)

        viewModel.getAllVotes().observe(this.viewLifecycleOwner, Observer {

            when(it) {
                is ApiCall.ConnectException -> {
                    voteProgress.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.ResponseError -> {
                    voteProgress.setInvisible()
                    showErrorMessage(it)
                }
                is ApiCall.Loading -> {
                    voteProgress.setVisible()
                }
                is ApiCall.Success -> {
                    voteProgress.setInvisible()
                }

            }
        })

        viewModel.submitVoteRez.observe(this.viewLifecycleOwner, Observer {
            when(it) {
                is ApiCall.Loading -> {
                    voteViewPager.isUserInputEnabled = false
                    voteProgress.setVisible()
                }
                is ApiCall.Success -> {
                    voteProgress.setInvisible()
                    voteViewPager.isUserInputEnabled = true
                }
                is ApiCall.ResponseError -> {
                    voteProgress.setInvisible()
                    voteViewPager.isUserInputEnabled = true
                }
                is ApiCall.ConnectException -> {
                    voteProgress.setInvisible()
                    voteViewPager.isUserInputEnabled = true
                }
            }
        })
    }
}
