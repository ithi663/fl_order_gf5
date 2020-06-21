package com.randomgametpnv.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.randomgametpnv.base.MainViewModel
import com.randomgametpnv.base.setInvisible
import com.randomgametpnv.base.setVisible
import com.randomgametpnv.login.entities.LoginState
import com.randomgametpnv.navigation.actionLaunchFragmentToHomeFragmentId
import com.randomgametpnv.navigation.actionLaunchFragmentToLoginFragmentId
import com.randomgametpnv.navigation.actionLoginFragmentToHomeFragmentId
import kotlinx.android.synthetic.main.fragment_launch.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class LaunchFragment : Fragment() {

    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkLogin().observe(this.viewLifecycleOwner, Observer {

            when (it) {

                LoginState.NoInternet -> {
                    launchProgressBar.setInvisible()
                    nameText.setVisible()
                    messageText.setVisible()
                }

                is LoginState.Login -> {
                    val navId = activity?.actionLaunchFragmentToHomeFragmentId()?: return@Observer
                    findNavController().navigate(navId)
                }
                LoginState.Logout -> {
                    val navId = activity?.actionLaunchFragmentToLoginFragmentId()?: return@Observer
                    findNavController().navigate(navId)
                }
            }
        })
    }

}