package com.randomgametpnv.help.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.randomgametpnv.base.initTopHeader
import com.randomgametpnv.help.R
import com.randomgametpnv.help.ui.util.HelpViewModelFactory
import kotlinx.android.synthetic.main.fragment_help.*
import org.koin.android.ext.android.inject


class HelpFragment : Fragment() {

    private val viewModelFactory: HelpViewModelFactory by inject()
    lateinit var viewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            navGraphViewModels<HelpViewModel>(R.id.helpFragment) { viewModelFactory }.value


        val topText = resources.getText(com.randomgametpnv.base.R.string.help).toString()
        this.initTopHeader(topText = topText, arrowVisibility = true, view = view)

        votingButton.setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_votingFragment)
        }

        accountsButton.setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_billsFragment)
        }

        journalButton.setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_jornalFragment)
        }
    }
}
